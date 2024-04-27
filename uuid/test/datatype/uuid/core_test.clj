(ns datatype.uuid.core-test
  (:require
   [clojure.test :refer [deftest]]

   [datatype.uuid.core :as dt-uuid]
   [datatype.testing.cases :as dt-test-cases]))

(deftest uuid-string?
  (dt-test-cases/assert-cases-satisfied-by dt-uuid/uuid-string?
    (dt-test-cases/true-case "any UUID string"
      :samples ["c09ac12c-036a-43b4-9ac1-2c036a43b4c9"
                "0081F399-287F-42CE-81F3-99287F22CE3D"
                "00000000-0000-0000-0000-000000000000"
                "ffffffff-ffff-ffff-ffff-ffffffffffff"
                "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"])
    (dt-test-cases/false-case "incorrectly formatted UUID strings"
      :samples ["c09a-c12c-036a-43b4-9ac1-2c03-6a43-b4c9"
                "0081F399287F42CE81F399287F22CE3D"
                "00000000-0000-0000-0000-0000"])
    (dt-test-cases/false-case "strings that aren't UUID-like at all"
      :samples ["the quick brown fox jumped over the lazy dog"
                "23.6"
                "true"])
    (dt-test-cases/false-case "a non-string"
      :samples [true false 35.4 #{"GBP" "USD"}])
    (dt-test-cases/false-case "nil" :sample nil)))
