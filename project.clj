(defproject io.logicblocks/datatype "0.0.1-RC9"
  :description "Aggregate project for all datatype modules."

  :parent-project {:path    "parent/project.clj"
                   :inherit [:scm
                             :url
                             :license
                             :plugins
                             [:profiles :parent-shared]
                             :deploy-repositories
                             :managed-dependencies]}

  :plugins [[io.logicblocks/lein-interpolate "0.1.1-RC3"]
            [lein-parent "0.3.9"]
            [lein-sub "0.3.0"]
            [lein-changelog "0.3.2"]
            [lein-codox "0.10.8"]]

  :sub ["parent"
        "core"
        "testing"
        "string"
        "address"
        "bool"
        "collection"
        "currency"
        "domain"
        "email"
        "network"
        "number"
        "phone"
        "time"
        "uri"
        "uuid"
        "."]

  :dependencies [[io.logicblocks/datatype.core]
                 [io.logicblocks/datatype.testing]
                 [io.logicblocks/datatype.address]
                 [io.logicblocks/datatype.bool]
                 [io.logicblocks/datatype.collection]
                 [io.logicblocks/datatype.currency]
                 [io.logicblocks/datatype.domain]
                 [io.logicblocks/datatype.email]
                 [io.logicblocks/datatype.network]
                 [io.logicblocks/datatype.number]
                 [io.logicblocks/datatype.phone]
                 [io.logicblocks/datatype.string]
                 [io.logicblocks/datatype.time]
                 [io.logicblocks/datatype.uri]
                 [io.logicblocks/datatype.uuid]]

  :profiles
  {:codox-specific
   {:dependencies [[io.logicblocks/core :project/version]
                   [org.clojure/data.csv]
                   [com.googlecode.libphonenumber/libphonenumber]
                   [lambdaisland/uri]
                   [com.widdindustries/cljc.java-time]]

    :source-paths ["core/src"
                   "testing/src"
                   "address/src"
                   "bool/src"
                   "collection/src"
                   "currency/src"
                   "domain/src"
                   "email/src"
                   "network/src"
                   "number/src"
                   "phone/src"
                   "string/src"
                   "time/src"
                   "uri/src"
                   "uuid/src"]}

   :test
   {:aliases {"eftest"
              ["sub"
               "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid"
               "with-profile" "test"
               "eftest"]}}

   :codox
   [:parent-shared :codox-specific]

   :prerelease
   {:release-tasks
    [
     ["vcs" "assert-committed"]
     ["sub" "change" "version" "leiningen.release/bump-version" "rc"]
     ["sub" "change" "version" "leiningen.release/bump-version" "release"]
     ["vcs" "commit" "Pre-release version %s [skip ci]"]
     ["vcs" "tag"]
     ["sub" "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid:." "deploy"]]}

   :release
   {:release-tasks
    [["vcs" "assert-committed"]
     ["sub" "change" "version" "leiningen.release/bump-version" "release"]
     ["sub" "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid:." "install"]
     ["changelog" "release"]
     ["shell" "sed" "-E" "-i.bak" "s/datatype\\.(.+) \"[0-9]+\\.[0-9]+\\.[0-9]+\"/datatype.\\\\1 \"${:version}\"/g" "README.md"]
     ["shell" "rm" "-f" "README.md.bak"]
     ["codox"]
     ["shell" "git" "add" "."]
     ["vcs" "commit" "Release version %s [skip ci]"]
     ["vcs" "tag"]
     ["sub" "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid:." "deploy"]
     ["sub" "change" "version" "leiningen.release/bump-version" "patch"]
     ["sub" "change" "version" "leiningen.release/bump-version" "rc"]
     ["sub" "change" "version" "leiningen.release/bump-version" "release"]
     ["vcs" "commit" "Pre-release version %s [skip ci]"]
     ["vcs" "tag"]
     ["vcs" "push"]]}}

  :source-paths []
  :test-paths []
  :resource-paths []

  :codox
  {:namespaces  [#"^datatype\."]
   :metadata    {:doc/format :markdown}
   :output-path "docs"
   :doc-paths   ["docs"]
   :source-uri  "https://github.com/logicblocks/datatype/blob/{version}/{filepath}#L{line}"}

  :aliases {"install"
            ["do"
             ["sub"
              "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid"
              "install"]
             ["install"]]

            "eastwood"
            ["sub"
             "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid"
             "eastwood"]

            "cljfmt"
            ["sub"
             "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid"
             "cljfmt"]

            "kibit"
            ["sub"
             "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid"
             "kibit"]

            "check"
            ["sub"
             "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid"
             "check"]

            "bikeshed"
            ["sub"
             "-s" "core:testing:string:address:bool:collection:currency:domain:email:network:number:phone:time:uri:uuid"
             "bikeshed"]})
