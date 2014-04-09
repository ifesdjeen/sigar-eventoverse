(ns sigar-eventoverse.core
  (:import [java.lang.Character]
           [org.hyperic.sigar Sigar SigarLoader])
  (:use [sigar-eventoverse.conversion]))

(def instance (Sigar.))

(defn get-cpu-list
  "List of cpus and their info"
  []
  (map to-map (.getCpuInfoList instance)))

(defn get-cpu-perc-breakdown
  "Current cpu stats"
  []
  (mapv to-map (.getCpuPercList instance)))

(defn get-proc-mem
  "Process memory metrics"
  [pid]
  (to-map (.getProcMem instance pid)))

(defn get-proc-cpu
  "Process memory metrics"
  [pid]
  (assoc (to-map (.getProcCpu instance pid))
    :pid pid))

(defn get-cpu-perc-combined
  "Current cpu stats"
  []
  (to-map (.getCpuPerc instance)))

(defn get-mem
  "Current cpu stats"
  []
  (to-map (.getMem instance)))

(defn get-netstat
  "Current cpu stats"
  []
  (to-map (.getNetStat instance)))

(defn get-netstat
  "Current cpu stats"
  []
  (to-map (.getNetStat instance)))

(defn get-tcp
  "Tcp stats"
  []
  (to-map (.getTcp instance)))

(defn get-swap
  "Tcp stats"
  []
  (to-map (.getSwap instance)))

(defn get-disk-usage
  "Disk usage for certain disk"
  [^String disk]
  (to-map (.getDiskUsage instance disk)))

(defn get-fs-usage
  "Disk usage for certain disk"
  [^String fs]
  (to-map (.getFileSystemUsage instance fs)))
