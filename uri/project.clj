(defproject io.logicblocks/datatype.uri "0.0.1-RC11"
  :description "Utility functions for URI data types."

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

  :dependencies [[org.clojure/data.csv]
                 [lambdaisland/uri]
                 [io.logicblocks/icu4clj]
                 [io.logicblocks/datatype.core]
                 [io.logicblocks/datatype.domain]
                 [io.logicblocks/datatype.network]
                 [io.logicblocks/datatype.string]]

  :profiles
  {:shared     ^{:pom-scope :test}
               {:dependencies [[io.logicblocks/datatype.testing]]}
   :reveal     [:parent-reveal :shared]
   :flow-storm [:parent-flow-storm :shared]
   :dev        [:parent-dev :shared]
   :test       [:parent-test :shared]})
