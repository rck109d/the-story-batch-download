the-story-batch-download
========================

### What

A batch download tool of content from The Story with Dick Gordon at www.thestory.org

### How

This project relies upon the presence of [Gradle](http://www.gradle.org/) and [Firefox](https://mozilla.org/firefox) (run by the Selenium driver).

To run (with Gradle already installed) open a command-line interface in the project and execute.

``
gradle run
``

This will create the directory `out` where every story will be saved.

Stories are saved within a directory with the naming format `2006-02-16-our-very-first-episode`

Each story directory has `description.txt`, `title.txt`, and `2006-02-16-our-very-first-episode.mp3`

### How Much

There are around 3,000 stories with a combined size of around 40 GB.

### Issues

##### Naming Overlap 

The above naming scheme is not unique for multiple stories with the same title on the same day.

This will lead to conflicts such as 

http://www.thestory.org/stories/2008-04/ahmeds-diary

http://www.thestory.org/stories/2008-04/ahmeds-diary-0

##### Tags

Tags (useful bits of categorical info) are currently being extracted but not yet saved.