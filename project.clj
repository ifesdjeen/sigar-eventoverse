(defproject sigar-eventoverse "1.0.0-beta1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :java-source-paths ["src/jvm"]
  :source-paths ["src/clj"]
  :jvm-opts [~(str "-Djava.library.path=./lib/:" (System/getenv "LD_LIBRARY_PATH"))]
  :native-path "native"
  :dependencies [[org.hyperic/sigar "1.6.5.132"]
                 [org.clojure/clojure "1.4.0"]]
  :repositories {"jboss" "https://repository.jboss.org/nexus/content/repositories/thirdparty-uploads"})
