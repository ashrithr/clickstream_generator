ClickStream Data Generator
-------------------------

Build
-----
Requirement to build the project:

* [sbt](http://www.scala-sbt.org/) (version 0.13.0)

This Generator generates data based on configuration file located at `resources/generator.conf`, edit the configuration
to suit your requirements and then build the project using sbt.

Once, sbt is installed run `assembly` sbt task from project root

```
sbt assembly
```

Running
-------

```
java -cp target/scala-2.10/clickstream_generator-*.jar com.cloudwick.generator.clickstream.Main
```

License and Authors
-------------------
Authors: [Ashrith](http://github.com/ashrithr)

Apache 2.0. Please see `LICENSE.txt`. All contents copyright (c) 2013, Cloudwick Labs.

