(ns datatype.uri.core-test
  (:require
   [clojure.test :refer [deftest]]

   [datatype.uri.core :as dt-uri]

   [datatype.testing.cases :as dt-test-cases]))

(deftest http-uri-string?
  (dt-test-cases/assert-cases-satisfied-by dt-uri/http-uri-string?
    (dt-test-cases/true-case "any absolute HTTP URI"
      :samples ["http://example.com/some-path"
                "https://example.com:9383"
                "http://example.com/path/to/file.csv"
                "https://example.com?query=param"])
    (dt-test-cases/false-case "any absolute non-HTTP URI"
      :samples ["file:///some-file"
                "file:/path/to/file.csv"
                "sftp://example.com/some/important/file.html"
                "ssh://user@host.com"])
    (dt-test-cases/false-case "any relative URI"
      :samples ["/some-path"
                "some/important/path"
                "/path/to/file.csv#fragment"
                "?query=param"
                "example.com/some/path"])
    (dt-test-cases/false-case "a non-string"
      :samples [true false 35.4 #{"GBP" "USD"}])
    (dt-test-cases/false-case "nil" :sample nil)))
