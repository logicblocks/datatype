(defproject io.logicblocks/datatype.string "0.0.1-RC5"
  :description "Utility functions for string data types."

  :parent-project {:path    "../parent/project.clj"
                   :inherit [:scm
                             :url
                             :license
                             :plugins
                             [:profiles :parent-shared]
                             [:profiles :parent-dev]
                             [:profiles :parent-dev-specific]
                             [:profiles :parent-reveal]
                             [:profiles :parent-reveal-specific]
                             [:profiles :parent-flow-storm]
                             [:profiles :parent-flow-storm-specific]
                             [:profiles :parent-test]
                             [:profiles :parent-test-specific]
                             :deploy-repositories
                             :managed-dependencies
                             :cloverage
                             :bikeshed
                             :cljfmt
                             :eastwood]}

  :plugins [[lein-parent "0.3.8"]]

  :dependencies [[borkdude/dynaload]
                 [io.logicblocks/icu4clj]
                 [io.logicblocks/datatype.core]]

  :profiles
  {:shared     ^{:pom-scope :test}
               {:dependencies
                [[org.clojure/test.check]
                 [io.logicblocks/datatype.testing]]}
   :reveal     [:parent-reveal :shared]
   :flow-storm [:parent-flow-storm :shared]
   :dev        [:parent-dev :shared]
   :test       [:parent-test :shared]})
