# Eventoverse Sigar

Little probe library to collect system-level metrics from your machines. Works with

## Supported operations

`get-cpu-list` List of cpus and their info
`get-cpu-perc` Current cpu stats
`get-proc-mem` Process memory metrics
`get-disk-usage` Disk usage for certain disk
`get-tcp` Tcp stats

Reporting helpers:

`map-diff` Returns numeric difference between values of two maps. Useful for metric diff reporting.

## License

Copyright © 2013 Alex Petrov

Distributed under the Eclipse Public License, the same as Clojure.

`lib` directory contains binaries of [Hyperic Sigar](http://www.hyperic.com/products/sigar)

Hyperic SIGAR is licensed under the terms of the Apache 2.0 license.

Some Java classes, included in early prototypes, and may be found under `jvm` folder were taken
from Sigar examples. Corresponding Licenses are in file headers.
