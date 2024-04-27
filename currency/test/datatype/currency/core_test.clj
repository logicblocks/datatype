(ns datatype.currency.core-test
  (:require
   [clojure.test :refer [deftest]]

   [datatype.currency.core :as dt-currency]
   [datatype.testing.cases :as dt-test-cases]))

(deftest iso4217-currency-code-string?-as-predicate
  (dt-test-cases/assert-cases-satisfied-by dt-currency/iso4217-currency-code-string?
    (dt-test-cases/true-case "any currency code string"
      :samples ["GBP" "BAM" "CUC" "MKN" "BOV" "ARP"])
    (dt-test-cases/false-case "valid but lowercase currency code strings"
      :samples ["gbp" "bam" "cuc" "mkn" "bov" "arp"])
    (dt-test-cases/false-case "additional whitespace"
      :samples [" GBP " "\tBAM" " CUC "])
    (dt-test-cases/false-case "any monetary amount string"
      :samples ["GBP 500.25" "BAM 0.15" "CUC 1000" "MKN -30.28"])
    (dt-test-cases/false-case
      "3 letter uppercase strings that look like currency codes"
      :samples ["ACU" "BGX" "HSM" "NSB"])
    (dt-test-cases/false-case "currency symbols"
      :samples ["$" "₫" "€" "¤" "₹" "¥" "£" "₪" "₱" "₩"])
    (dt-test-cases/false-case "a non-string"
      :samples [true false 35.4 #{"GBP" "USD"}])
    (dt-test-cases/false-case "nil" :sample nil)))
