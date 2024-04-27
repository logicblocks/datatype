(ns datatype.bool.core-test
  (:require
   [clojure.test :refer [deftest]]

   [datatype.bool.core :as dt-bool]
   [datatype.testing.cases :as dt-test-cases]))

(deftest boolean-string?-as-predicate
  (dt-test-cases/assert-cases-satisfied-by dt-bool/boolean-string?
    (dt-test-cases/true-case "boolean strings"
      :samples ["true" "false" "TRUE" "FALSE" "True" "False"])
    (dt-test-cases/false-case "booleans"
      :samples [true false
                Boolean/TRUE Boolean/FALSE
                (Boolean/valueOf true) (Boolean/valueOf false)])
    (dt-test-cases/false-case "non-strings"
      :samples [35.4 #{"GBP" "USD"}])
    (dt-test-cases/false-case "nil" :sample nil)))
