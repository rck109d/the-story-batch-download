the-story-batch-download
========================

### What

A batch download tool of content from The Story with Dick Gordon at www.thestory.org

### How

This project uses [Gradle](http://www.gradle.org/).

To run: open a command-line interface in the project and execute

``
gradle run
``

Running creates the `out` directory where every story will be saved.

Stories are saved within a directory with the naming format `2006-02-16-our-very-first-episode`

Each story directory has `description.txt`, `title.txt`, `tags.csv`, and `2006-02-16-our-very-first-episode.mp3`

### How Much

There are around 3,000 stories with a combined size of around 40 GB.
