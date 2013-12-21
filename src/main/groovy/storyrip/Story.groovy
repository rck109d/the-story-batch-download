package storyrip
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.joda.time.DateTime
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
class Story {
  static Document getDoc(url){Jsoup.connect(url).get()}
  static void main(args) {
    (296..0).each {pageNumber->
      println "PAGE $pageNumber"
      getDoc("http://www.thestory.org/stories?page=$pageNumber").select('#inset-content .views-group').collect{Element element->
        element.select('.views-row').collect{Element row->
          row.select('.node-title a').attr('href')
        }
      }.flatten().reverse().each {String storyURL->
        def doc = getDoc("http://www.thestory.org$storyURL")
        def title = doc.select('.page-title').text()?:''
        def description = doc.select('.field-name-field-body').text()?:''
        def tags = doc.select('.node-field-tags .field-items a').collect{it.text()}
        def date = new DateTime(doc.select('.date-display-single').attr('content')).toDate()
        def titleForDir = storyURL[storyURL.lastIndexOf('/')+1..-1].toLowerCase()
        for(c in '?/\\:*|<>".') {
          titleForDir = titleForDir.replace(c, '')
        }
        def fileName = "${date.format('yyyy-MM-dd')}-$titleForDir"
        def outDir = new File("out/$fileName")
        outDir.mkdirs()
        new File(outDir, 'title.txt').write(title)
        new File(outDir, 'description.txt').write(description)
        new File(outDir, 'tags.csv').write(tags.join(','))
        def downloadAnchor = doc.select('.fieldlayout-body .page-title')[0].parent().select('.mediaplaylist-links .link-play a').attr('href')
        try {
          def connection = new URL(downloadAnchor).openConnection()
          def downloadFile = new File(outDir, "${fileName}.mp3")
          if(!downloadFile.isFile() || downloadFile.length() != connection.getContentLength()) {
            downloadFile << connection.getContent()
            println "downloaded $fileName"
          } else {
            println "existing $fileName"
          }
        } catch (any) {
          new File(outDir, 'unable to download MP3').createNewFile()
          println "unable to download $fileName"
        }
      }
    }
  }
}
