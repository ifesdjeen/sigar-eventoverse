(ns sigar-eventoverse.conversion
  (:import [java.lang.Character]
           [org.hyperic.sigar CpuInfo CpuPerc ProcMem DiskUsage Tcp]))

;;
;; Implementation
;;

(defn camel-to-dash
  "Converts camel-cased string to dash-separated"
  [s]
  (keyword (subs (apply str (map #(if (Character/isUpperCase %)
                                    (str "-" (clojure.string/lower-case %))
                                    %)
                                 s))
                 1)))

(defn- apply-to-keys [m f]
  "Applies function f to all values in map m"
  (into {} (for [[k v] m]
             [(f k) v])))

(defn- apply-to-values [m f]
  "Applies function f to all values in map m"
  (into {} (for [[k v] m]
             [k (f v)])))

(defn numerize
  [v]
  (if (string? v)
    (read-string v)
    v))

(defn- numerize-values
  [m]
  (apply-to-values m numerize))

;;
;; ToMap
;;

(defprotocol ToMap
  (to-map [input]))

(extend-protocol ToMap
  CpuInfo
  (to-map [^CpuInfo cpu-info]
    (numerize-values
     (apply-to-keys
      (.toMap cpu-info)
      camel-to-dash)))

  CpuPerc
  (to-map [^CpuPerc cpu-perc]
    (numerize-values
     {:user (.getUser cpu-perc)
      :sys (.getSys cpu-perc)
      :idle (.getIdle cpu-perc)
      :wait (.getWait cpu-perc)
      :nice (.getNice cpu-perc)
      :combined (.getCombined cpu-perc)
      :irc (.getIrq cpu-perc)}))

  ProcMem
  (to-map [^ProcMem proc-mem]
    (numerize-values
     {:size (.getSize proc-mem)
      :resident (.getResident proc-mem)
      :share (.getShare proc-mem)
      :minor-faults (.getMinorFaults proc-mem)
      :major-faults (.getMajorFaults proc-mem)})
    )

  DiskUsage
  (to-map [^DiskUsage disk-usage]
    (numerize-values
     (apply-to-keys
      (into {} (.toMap disk-usage))
      camel-to-dash
      )))

  Tcp
  (to-map [^Tcp tcp]
    (numerize-values
     (apply-to-keys
      (into {} (.toMap tcp))
      camel-to-dash))))
