(ns sigar-eventoverse.core-test
  (:use clojure.test
        sigar-eventoverse.core))

(deftest a-test
  (println (get-cpu-list))
  (println (get-cpu-perc))
  (println (get-proc-mem 80450))
  (println (get-disk-usage "/dev/disk0s2"))
  (println (map-diff {:passive-opens 6408, :attempt-fails 22534, :out-segs 9201522, :retrans-segs 17668, :active-opens 129628, :in-segs 12955160, :in-errs 22, :estab-resets 4894, :curr-estab 102} (get-tcp))))
