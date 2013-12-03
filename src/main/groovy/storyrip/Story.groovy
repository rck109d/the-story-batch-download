package storyrip
import geb.Browser
import geb.navigator.Navigator
import groovyx.net.http.HTTPBuilder

import org.joda.time.DateTime
class Story {
  static def maxStoryPage = 296
  static String queryPageURL(pageNumber) {
    "http://www.thestory.org/stories?page=$pageNumber"
  }
  static void downloadStories() {
    Browser b = new Browser()
    (maxStoryPage..0).each {pageNumber->
      println "PAGE $pageNumber"
      b.go(queryPageURL(pageNumber))
      def storyURLs = []
      b.$('#inset-content .views-group').each {Navigator nav ->
        def dateString = nav.find('.views-group-header .date-display-single').attr('content')
        def date = new org.joda.time.DateTime(dateString).toDate()
        nav.find('.views-row').each { row ->
          def anchor = row.find('.node-title a')
          storyURLs << anchor.attr('href')
        }
      }
      storyURLs.reverse(true)
      storyURLs.each {
        b.go(it)
        def title = b.$('.page-title').text()?:''
        def description = b.$('.field-name-field-body').text()?:''
        def tags = b.$('.node-field-tags .field-items a').collect{it.text()} // TODO: store tags
        Date date = new DateTime(b.$('.date-display-single').attr('content')).toDate()
        
        def titleForDir = title.toLowerCase().replace(' ', '-')
        for(c in '?/\\:*|<>".') {
          titleForDir = titleForDir.replace(c, '')
        }
        
        def fileName = date.format('yyyy-MM-dd') + '-' + titleForDir
        
        println "processing $fileName"
        def outDir = new File("out/$fileName")
        outDir.mkdirs()
        
        new File(outDir, 'title.txt').write(title)
        new File(outDir, 'description.txt').write(description)
        
        def downloadAnchor = b.$('.fieldlayout-body .page-title').parent().find('.mediaplaylist-link-download')
        try {
          new File(outDir, "${fileName}.mp3") << b.downloadBytes(downloadAnchor.@href)
        } catch (any) {
          new File(outDir, "unable to download MP3").createNewFile()
        }
      }
    }
    b.quit()
  }
}
