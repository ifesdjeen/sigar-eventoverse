(ns sigar-eventoverse.core
  (:import [java.lang.Character]
           [org.hyperic.sigar Sigar SigarLoader])
  (:use [sigar-eventoverse.conversion]))

(defn get-cpu-list
  "List of cpus and their info"
  []
  (map to-map (.getCpuInfoList (Sigar.))))

(defn get-cpu-perc
  "Current cpu stats"
  []
  (to-map (.getCpuPerc (Sigar.))))

(defn get-proc-mem
  "Process memory metrics"
  [pid]
  (to-map (.getProcMem (Sigar.) pid)))

(defn get-disk-usage
  "Disk usage for certain disk"
  [disk]
  (to-map (.getDiskUsage (Sigar.) disk)))

(defn get-tcp
  "Tcp stats"
  []
  (to-map (.getTcp (Sigar.))))

(defn map-diff [m1 m2]
  "Returns numeric difference between values of two maps. Useful for diff reporting."
  (into {} (for [[k v] m2]
             [k (- v (get m1 k))])))