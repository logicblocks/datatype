(defproject io.logicblocks/datatype.parent "0.0.1-RC2"
  :scm {:dir  "."
        :name "git"
        :url  "https://github.com/logicblocks/datatype"}

  :url "https://github.com/logicblocks/datatype"

  :license
  {:name "The MIT License"
   :url  "https://opensource.org/licenses/MIT"}

  :plugins [[io.logicblocks/lein-interpolate "0.1.1-RC3"]
            [jonase/eastwood "1.4.2"]
            [lein-ancient "0.7.0"]
            [lein-bikeshed "0.5.2"]
            [lein-cljfmt "0.9.2"]
            [lein-cloverage "1.2.4"]
            [lein-cprint "1.3.3"]
            [lein-eftest "0.6.0"]
            [lein-kibit "0.1.8"]
            [lein-shell "0.5.0"]
            [fipp "0.6.26"]]

  :deploy-repositories
  {"releases"  {:url "https://repo.clojars.org" :creds :gpg}
   "snapshots" {:url "https://repo.clojars.org" :creds :gpg}}

  :managed-dependencies
  [[org.clojure/clojure "1.11.1"]
   [org.clojure/data.csv "1.1.0"]
   [org.clojure/test.check "1.1.1"]

   [io.logicblocks/icu4clj "0.0.1-RC2"]

   [io.logicblocks/datatype.core :project/version]
   [io.logicblocks/datatype.testing :project/version]
   [io.logicblocks/datatype.address :project/version]
   [io.logicblocks/datatype.bool :project/version]
   [io.logicblocks/datatype.collection :project/version]
   [io.logicblocks/datatype.currency :project/version]
   [io.logicblocks/datatype.domain :project/version]
   [io.logicblocks/datatype.email :project/version]
   [io.logicblocks/datatype.network :project/version]
   [io.logicblocks/datatype.number :project/version]
   [io.logicblocks/datatype.phone :project/version]
   [io.logicblocks/datatype.string :project/version]
   [io.logicblocks/datatype.time :project/version]
   [io.logicblocks/datatype.uri :project/version]
   [io.logicblocks/datatype.uuid :project/version]

   [com.googlecode.libphonenumber/libphonenumber "8.13.12"]

   [lambdaisland/uri "1.19.155"]

   [com.widdindustries/cljc.java-time "0.1.21"]

   [nrepl "1.1.1"]

   [eftest "0.6.0"]

   [vlaaad/reveal "1.3.282"]

   [com.github.flow-storm/clojure "1.11.2-4"]
   [com.github.flow-storm/flow-storm-dbg "3.15.1"]]

  :profiles
  {:parent-shared
   ^{:pom-scope :test}
   {:dependencies
    [[org.clojure/clojure]
     [org.clojure/test.check]

     [com.github.flow-storm/clojure]
     [com.github.flow-storm/flow-storm-dbg]

     [vlaaad/reveal]

     [nrepl]

     [eftest]]}

   :parent-dev-specific
   ^{:pom-scope :test}
   {:source-paths ["dev"]
    :eftest       {:multithread? false}}

   :parent-flow-storm-specific
   ^{:pom-scope :test}
   {:exclusions [org.clojure/clojure]
    :jvm-opts   ["-Dclojure.storm.instrumentEnable=true"
                 "-Dclojure.storm.instrumentOnlyPrefixes=spec.validate,clojure.spec"]}

   :parent-reveal-specific
   ^{:pom-scope :test}
   {:repl-options {:nrepl-middleware [vlaaad.reveal.nrepl/middleware]}
    :jvm-opts     ["-Dvlaaad.reveal.prefs={:theme :light}"]}

   :parent-test-specific
   ^{:pom-scope :test}
   {:eftest {:multithread? false}}

   :parent-dev
   ^{:pom-scope :test}
   [:parent-shared :parent-dev-specific]

   :parent-flow-storm
   ^{:pom-scope :test}
   [:parent-shared :parent-flow-storm-specific]

   :parent-reveal
   ^{:pom-scope :test}
   [:parent-shared :parent-reveal-specific]

   :parent-test
   ^{:pom-scope :test}
   [:parent-shared :parent-test-specific]}

  :source-paths []
  :test-paths []
  :resource-paths []

  :cloverage
  {:ns-exclude-regex [#"^user"]}

  :bikeshed
  {:name-collisions false
   :long-lines      false}

  :cljfmt
  {:indents {#".*"     [[:inner 0]]
             defrecord [[:block 1] [:inner 1]]
             deftype   [[:block 1] [:inner 1]]}}

  :eastwood
  {:config-files
   [~(str (System/getProperty "user.dir") "/config/linter.clj")]})
