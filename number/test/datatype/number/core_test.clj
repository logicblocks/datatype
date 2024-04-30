(ns datatype.number.core-test
  (:require
   [clojure.test :refer [deftest testing]]

   [datatype.number.core :as dt-number]

   [datatype.testing.cases :as dt-test-cases])
  (:import [java.util Locale]))

(deftest positive?
  (dt-test-cases/assert-cases-satisfied-by dt-number/positive?
    (dt-test-cases/true-case "any positive int"
      :samples [(int 1) (int 35) (int 100000) Integer/MAX_VALUE])
    (dt-test-cases/true-case "any positive long"
      :samples [1 35 100000 Long/MAX_VALUE])
    (dt-test-cases/true-case "any positive float"
      :samples [(float 0.00000000000001)
                (float 1.23) (float 35.456) (float 100000)
                Float/MIN_VALUE
                Float/MAX_VALUE])
    (dt-test-cases/true-case "any positive double"
      :samples [0.000000000000000000001
                1.23 35.456 100000
                Double/MIN_VALUE
                Double/MAX_VALUE])
    (dt-test-cases/true-case "any positive big int"
      :samples [1N 35N 100000N
                (bigint "111111111111111999999999999991111111")])
    (dt-test-cases/true-case "any positive big integer"
      :samples [(biginteger 1) (biginteger 35) (biginteger 100000)
                (biginteger "111111111111111999999999999991111111")])
    (dt-test-cases/true-case "any positive big decimal"
      :samples [(bigdec 1.123) (bigdec 35.2) (bigdec 100000)
                (bigdec "111111111111111999999999999991111111.4567")])
    (dt-test-cases/false-case "any zero value number"
      :samples [(int 0) 0 (float 0) 0.0 0M 0N (biginteger 0)])
    (dt-test-cases/false-case "any negative integer"
      :samples [(int -1) (int -35) (int -100000) Integer/MIN_VALUE])
    (dt-test-cases/false-case "any negative long"
      :samples [-1 -35 -100000 Long/MIN_VALUE])
    (dt-test-cases/false-case "any negative float"
      :samples [(float -0.00000000000001)
                (float -1.23) (float -35.456) (float -100000)
                (- Float/MIN_VALUE)
                (- Float/MAX_VALUE)])
    (dt-test-cases/false-case "any negative double"
      :samples [-0.000000000000000000001
                -1.23 -35.456 -100000
                (- Double/MIN_VALUE)
                (- Double/MAX_VALUE)])
    (dt-test-cases/false-case "any negative big int"
      :samples [(biginteger -1) (biginteger -35) (biginteger -100000)
                (bigint "-111111111111111999999999999991111111")])
    (dt-test-cases/false-case "any negative big integer"
      :samples [-1N -35N -100000N
                (biginteger "-111111111111111999999999999991111111")])
    (dt-test-cases/false-case "any negative big decimal"
      :samples [(bigdec -1.123) (bigdec -35.2) (bigdec -100000)
                (bigdec "-111111111111111999999999999991111111.4567")])
    (dt-test-cases/false-case "any number as a string"
      :samples ["1" "-1" "100.2" "-100.45"])
    (dt-test-cases/false-case "a non-number"
      :samples [true false "not-an-integer"])
    (dt-test-cases/false-case "nil" :sample nil)))

(deftest negative?
  (dt-test-cases/assert-cases-satisfied-by dt-number/negative?
    (dt-test-cases/true-case "any negative integer"
      :samples [(int -1) (int -35) (int -100000) Integer/MIN_VALUE])
    (dt-test-cases/true-case "any negative long"
      :samples [-1 -35 -100000 Long/MIN_VALUE])
    (dt-test-cases/true-case "any negative float"
      :samples [(float -0.00000000000001)
                (float -1.23) (float -35.456) (float -100000)
                (- Float/MIN_VALUE)
                (- Float/MAX_VALUE)])
    (dt-test-cases/true-case "any negative double"
      :samples [-0.000000000000000000001
                -1.23 -35.456 -100000
                (- Double/MIN_VALUE)
                (- Double/MAX_VALUE)])
    (dt-test-cases/true-case "any negative big int"
      :samples [(biginteger -1) (biginteger -35) (biginteger -100000)
                (bigint "-111111111111111999999999999991111111")])
    (dt-test-cases/true-case "any negative big integer"
      :samples [-1N -35N -100000N
                (biginteger "-111111111111111999999999999991111111")])
    (dt-test-cases/true-case "any negative big decimal"
      :samples [(bigdec -1.123) (bigdec -35.2) (bigdec -100000)
                (bigdec "-111111111111111999999999999991111111.4567")])
    (dt-test-cases/false-case "any zero value number"
      :samples [(int 0) 0 (float 0) 0.0 0M 0N (biginteger 0)])
    (dt-test-cases/false-case "any positive int"
      :samples [(int 1) (int 35) (int 100000) Integer/MAX_VALUE])
    (dt-test-cases/false-case "any positive long"
      :samples [1 35 100000 Long/MAX_VALUE])
    (dt-test-cases/false-case "any positive float"
      :samples [(float 0.00000000000001)
                (float 1.23) (float 35.456) (float 100000)
                Float/MIN_VALUE
                Float/MAX_VALUE])
    (dt-test-cases/false-case "any positive double"
      :samples [0.000000000000000000001
                1.23 35.456 100000
                Double/MIN_VALUE
                Double/MAX_VALUE])
    (dt-test-cases/false-case "any positive big int"
      :samples [1N 35N 100000N
                (bigint "111111111111111999999999999991111111")])
    (dt-test-cases/false-case "any positive big integer"
      :samples [(biginteger 1) (biginteger 35) (biginteger 100000)
                (biginteger "111111111111111999999999999991111111")])
    (dt-test-cases/false-case "any positive big decimal"
      :samples [(bigdec 1.123) (bigdec 35.2) (bigdec 100000)
                (bigdec "111111111111111999999999999991111111.4567")])
    (dt-test-cases/false-case "any number as a string"
      :samples ["1" "-1" "100.2" "-100.45"])
    (dt-test-cases/false-case "a non-number"
      :samples [true false "not-an-integer"])
    (dt-test-cases/false-case "nil" :sample nil)))

(deftest zero-number-spec-validation
  (dt-test-cases/assert-cases-satisfied-by dt-number/zero?
    (dt-test-cases/true-case "any zero value number"
      :samples [(int 0) 0 (float 0.0) 0.0 0M 0N (biginteger 0)])
    (dt-test-cases/false-case "any negative integer"
      :samples [(int -1) (int -35) (int -100000) Integer/MIN_VALUE])
    (dt-test-cases/false-case "any negative long"
      :samples [-1 -35 -100000 Long/MIN_VALUE])
    (dt-test-cases/false-case "any negative float"
      :samples [(float -0.00000000000001)
                (float -1.23) (float -35.456) (float -100000)
                (- Float/MIN_VALUE)
                (- Float/MAX_VALUE)])
    (dt-test-cases/false-case "any negative double"
      :samples [-0.000000000000000000001
                -1.23 -35.456 -100000
                (- Double/MIN_VALUE)
                (- Double/MAX_VALUE)])
    (dt-test-cases/false-case "any negative big int"
      :samples [(biginteger -1) (biginteger -35) (biginteger -100000)
                (bigint "-111111111111111999999999999991111111")])
    (dt-test-cases/false-case "any negative big integer"
      :samples [-1N -35N -100000N
                (biginteger "-111111111111111999999999999991111111")])
    (dt-test-cases/false-case "any negative big decimal"
      :samples [(bigdec -1.123) (bigdec -35.2) (bigdec -100000)
                (bigdec "-111111111111111999999999999991111111.4567")])
    (dt-test-cases/false-case "any positive int"
      :samples [(int 1) (int 35) (int 100000) Integer/MAX_VALUE])
    (dt-test-cases/false-case "any positive long"
      :samples [1 35 100000 Long/MAX_VALUE])
    (dt-test-cases/false-case "any positive float"
      :samples [(float 0.00000000000001)
                (float 1.23) (float 35.456) (float 100000)
                Float/MIN_VALUE
                Float/MAX_VALUE])
    (dt-test-cases/false-case "any positive double"
      :samples [0.000000000000000000001
                1.23 35.456 100000
                Double/MIN_VALUE
                Double/MAX_VALUE])
    (dt-test-cases/false-case "any positive big int"
      :samples [1N 35N 100000N
                (bigint "111111111111111999999999999991111111")])
    (dt-test-cases/false-case "any positive big integer"
      :samples [(biginteger 1) (biginteger 35) (biginteger 100000)
                (biginteger "111111111111111999999999999991111111")])
    (dt-test-cases/false-case "any positive big decimal"
      :samples [(bigdec 1.123) (bigdec 35.2) (bigdec 100000)
                (bigdec "111111111111111999999999999991111111.4567")])
    (dt-test-cases/false-case "any number as a string"
      :samples ["1" "-1" "100.2" "-100.45"])
    (dt-test-cases/false-case "a non-number"
      :samples [true false "not-an-integer"])
    (dt-test-cases/false-case "nil" :sample nil)))

(deftest integer-string?
  (testing "general cases"
    (dt-test-cases/assert-cases-satisfied-by dt-number/integer-string?
      (dt-test-cases/true-case "any integer as a string"
        :samples ["0" "-0" "+0" "35" "-35" "+35"
                  (str Integer/MIN_VALUE)
                  (str Integer/MAX_VALUE)])
      (dt-test-cases/false-case "any non-base 10 integer as a string"
        :samples ["0xff" "035" "2r1011" "7N" "36rCRAZY"])
      (dt-test-cases/false-case "many zeroes"
        :samples ["00", "0000000"])
      (dt-test-cases/false-case "leading zeroes"
        :samples ["00123" "-00123"])
      (dt-test-cases/false-case "multiple leading signs"
        :samples ["+-123" "-+45" "--0" "++10" "+-+23"])
      (dt-test-cases/false-case "any integer"
        :samples [0 35 -35
                  0xff 017 2r1011 7N 36rCRAZY
                  Integer/MIN_VALUE
                  Integer/MAX_VALUE])
      (dt-test-cases/false-case "a decimal" :sample 35.23)
      (dt-test-cases/false-case "a non-numeric string"
        :sample "not-an-integer")
      (dt-test-cases/false-case "a non-string"
        :samples [true false #{1 2 3}])
      (dt-test-cases/false-case "an empty string"
        :sample "")
      (dt-test-cases/false-case "nil" :sample nil)))

  (testing "locale specific cases"
    (dt-test-cases/assert-cases-satisfied-by dt-number/integer-string?
      (dt-test-cases/true-case
        "an integer string with correct thousands separator for the UK"
        :samples ["1,000" "10,000" "100,000" "1,000,000"]
        :locale (Locale/UK))
      (dt-test-cases/false-case
        "an integer string with incorrect thousands separator for the UK"
        :samples ["1.000" "10.000" "100.000" "1.000.000"]
        :locale (Locale/UK))
      (dt-test-cases/true-case
        "an integer string with correct thousands separator for Germany"
        :samples ["1.000" "10.000" "100.000" "1.000.000"]
        :locale (Locale/GERMANY))
      (dt-test-cases/false-case
        "an integer string with incorrect thousands separator for Germany"
        :samples ["1,000" "10,000" "100,000" "1,000,000"]
        :locale (Locale/GERMANY))
      (dt-test-cases/true-case
        "an integer string with thousands separator in the wrong place"
        :samples ["1,0" "1,00,0000"]
        :locale (Locale/UK)
        :note "TODO: This is treated as valid but shouldn't be...")
      (dt-test-cases/false-case "a decimal string"
        :sample "35.23"
        :locale (Locale/UK)))))

(deftest positive-integer-string?
  (testing "general cases"
    (dt-test-cases/assert-cases-satisfied-by
      dt-number/positive-integer-string?
      (dt-test-cases/true-case "integer strings without sign"
        :samples ["1" "10" "25" "10000000" (str Integer/MAX_VALUE)])
      (dt-test-cases/true-case "integer strings with plus sign"
        :samples ["+1" "+10" "+25" "+10000000" (str "+" Integer/MAX_VALUE)])
      (dt-test-cases/false-case "zero"
        :samples ["0" "+0" "-0"])
      (dt-test-cases/false-case "integer strings with minus sign"
        :samples ["-1" "-10" "-25" "-10000000" (str Integer/MIN_VALUE)])
      (dt-test-cases/false-case "any non-base 10 integer as a string"
        :samples ["0xff" "035" "2r1011" "7N" "36rCRAZY"])
      (dt-test-cases/false-case "many zeroes"
        :samples ["00", "0000000"])
      (dt-test-cases/false-case "leading zeroes"
        :samples ["00123" "+00123" "-00123"])
      (dt-test-cases/false-case "multiple leading signs"
        :samples ["+-123" "-+45" "--0" "++10" "+-+23"])
      (dt-test-cases/false-case "any integer"
        :samples [0 35 -35
                  0xff 017 2r1011 7N 36rCRAZY
                  Integer/MIN_VALUE
                  Integer/MAX_VALUE])
      (dt-test-cases/false-case "a decimal" :sample 35.23)
      (dt-test-cases/false-case "a non-numeric string"
        :sample "not-an-integer")
      (dt-test-cases/false-case "a non-string"
        :samples [true false #{1 2 3}])
      (dt-test-cases/false-case "an empty string"
        :sample "")
      (dt-test-cases/false-case "nil" :sample nil)))

  (testing "locale specific cases"
    (dt-test-cases/assert-cases-satisfied-by
      dt-number/positive-integer-string?
      (dt-test-cases/true-case
        "an integer string with correct thousands separator for the UK"
        :samples ["1,000" "10,000" "100,000" "1,000,000"]
        :locale (Locale/UK))
      (dt-test-cases/false-case
        "an integer string with incorrect thousands separator for the UK"
        :samples ["1.000" "10.000" "100.000" "1.000.000"]
        :locale (Locale/UK))
      (dt-test-cases/true-case
        "an integer string with correct thousands separator for Germany"
        :samples ["1.000" "10.000" "100.000" "1.000.000"]
        :locale (Locale/GERMANY))
      (dt-test-cases/false-case
        "an integer string with incorrect thousands separator for Germany"
        :samples ["1,000" "10,000" "100,000" "1,000,000"]
        :locale (Locale/GERMANY))
      (dt-test-cases/true-case
        "an integer string with thousands separator in the wrong place"
        :samples ["1,0" "1,00,0000"]
        :locale (Locale/UK)
        :note "TODO: This is treated as valid but shouldn't be...")
      (dt-test-cases/false-case "a decimal string"
        :sample "35.23"
        :locale (Locale/UK)))))

(deftest negative-integer-string?
  (testing "general cases"
    (dt-test-cases/assert-cases-satisfied-by
      dt-number/negative-integer-string?
      (dt-test-cases/true-case "integer strings with minus sign"
        :samples ["-1" "-10" "-25" "-10000000" (str Integer/MIN_VALUE)])
      (dt-test-cases/false-case "zero"
        :samples ["0" "+0" "-0"])
      (dt-test-cases/false-case "integer strings without sign"
        :samples ["1" "10" "25" "10000000" (str Integer/MAX_VALUE)])
      (dt-test-cases/false-case "integer strings with plus sign"
        :samples ["+1" "+10" "+25" "+10000000" (str "+" Integer/MAX_VALUE)])
      (dt-test-cases/false-case "any non-base 10 integer as a string"
        :samples ["0xff" "035" "2r1011" "7N" "36rCRAZY"])
      (dt-test-cases/false-case "many zeroes"
        :samples ["00", "0000000"])
      (dt-test-cases/false-case "leading zeroes"
        :samples ["00123" "+00123" "-00123"])
      (dt-test-cases/false-case "multiple leading signs"
        :samples ["+-123" "-+45" "--0" "++10" "+-+23"])
      (dt-test-cases/false-case "any integer"
        :samples [0 35 -35
                  0xff 017 2r1011 7N 36rCRAZY
                  Integer/MIN_VALUE
                  Integer/MAX_VALUE])
      (dt-test-cases/false-case "a decimal" :sample 35.23)
      (dt-test-cases/false-case "a non-numeric string"
        :sample "not-an-integer")
      (dt-test-cases/false-case "a non-string"
        :samples [true false #{1 2 3}])
      (dt-test-cases/false-case "an empty string"
        :sample "")
      (dt-test-cases/false-case "nil" :sample nil)))

  (testing "locale specific cases"
    (dt-test-cases/assert-cases-satisfied-by
      dt-number/negative-integer-string?
      (dt-test-cases/true-case
        "an integer string with correct thousands separator for the UK"
        :samples ["-1,000" "-10,000" "-100,000" "-1,000,000"]
        :locale (Locale/UK))
      (dt-test-cases/false-case
        "an integer string with incorrect thousands separator for the UK"
        :samples ["-1.000" "-10.000" "-100.000" "-1.000.000"]
        :locale (Locale/UK))
      (dt-test-cases/true-case
        "an integer string with correct thousands separator for Germany"
        :samples ["-1.000" "-10.000" "-100.000" "-1.000.000"]
        :locale (Locale/GERMANY))
      (dt-test-cases/false-case
        "an integer string with incorrect thousands separator for Germany"
        :samples ["-1,000" "-10,000" "-100,000" "-1,000,000"]
        :locale (Locale/GERMANY))
      (dt-test-cases/true-case
        "an integer string with thousands separator in the wrong place"
        :samples ["-1,0" "-1,00,0000"]
        :locale (Locale/UK)
        :note "TODO: This is treated as valid but shouldn't be...")
      (dt-test-cases/false-case "a decimal string"
        :sample "-35.23"
        :locale (Locale/UK)))))

(deftest decimal-string?
  (testing "general cases"
    (dt-test-cases/assert-cases-satisfied-by dt-number/decimal-string?
      (dt-test-cases/true-case "any decimal as a string"
        :samples ["0" "-0" "+0" "0." "-0." "+0."
                  "0.00" "-0.0000" "+0.000"
                  "0.82" "+0.82" "-0.82"
                  "35." "-35." "+35." "35" "-35" "+35"
                  "2.78" ".91" "+.72"])
      (dt-test-cases/false-case "any non-base 10 integer as a string"
        :samples ["0xff" "035" "2r1011" "7N" "36rCRAZY"])
      (dt-test-cases/false-case "many zeroes"
        :samples ["00", "0000000"])
      (dt-test-cases/false-case "leading zeroes"
        :samples ["00123" "00.123" "000.456"])
      (dt-test-cases/false-case "multiple leading signs"
        :samples ["+-123.4" "-+45" "--0.23" "++10.0" "+-+23.456"])
      (dt-test-cases/false-case "any integer"
        :samples [0 35 -35
                  0xff 017 2r1011 7N 36rCRAZY
                  Integer/MIN_VALUE
                  Integer/MAX_VALUE])
      (dt-test-cases/false-case "a floating point number" :sample 35.23)
      (dt-test-cases/false-case "a non-numeric string"
        :sample "not-a-decimal")
      (dt-test-cases/false-case "a non-string"
        :samples [true false #{1 2 3}])
      (dt-test-cases/false-case "an empty string"
        :sample "")
      (dt-test-cases/false-case "nil" :sample nil)))

  (testing "locale specific cases"
    (dt-test-cases/assert-cases-satisfied-by dt-number/decimal-string?
      (dt-test-cases/true-case
        "a decimal string with correct thousands separator for the UK"
        :samples ["1,000.456" "10,000" "100,000.1" "1,000,000."]
        :locale (Locale/UK))
      (dt-test-cases/false-case
        "a decimal string with incorrect thousands separator for the UK"
        :samples ["1.000,56" "10.000," "100.000,1" "1.000.000,"]
        :locale (Locale/UK))
      (dt-test-cases/true-case
        "a decimal string with correct thousands separator for Germany"
        :samples ["1.000,123" "10.000" "100.000,4" "1.000.000,"]
        :locale (Locale/GERMANY))
      (dt-test-cases/false-case
        "a decimal string with incorrect thousands separator for Germany"
        :samples ["1,000.456" "10,000.0" "100,000.1" "1,000,000."]
        :locale (Locale/GERMANY))
      (dt-test-cases/true-case
        "a decimal string with correct thousands separator for France"
        :samples ["1 000,456"
                  "10 000,0"
                  "100 000,1"
                  "1 000 000,"]
        :locale (Locale/FRANCE))
      (dt-test-cases/false-case
        "a decimal string with incorrect thousands separator for France"
        :samples ["1,000.123" "10.000,0" "100,000.4" "1.000.000,"]
        :locale (Locale/FRANCE))
      (dt-test-cases/true-case
        "a decimal string with thousands separator in the wrong place"
        :samples ["1,0.1" "1,00,0000."]
        :locale (Locale/UK)
        :note "TODO: This is treated as valid but shouldn't be..."))))

(deftest positive-decimal-string?
  (testing "general cases"
    (dt-test-cases/assert-cases-satisfied-by
      dt-number/positive-decimal-string?
      (dt-test-cases/true-case "any positive decimal as a string with no sign"
        :samples ["0.82" "35." "35" "2.78" ".91"])
      (dt-test-cases/true-case "any decimal as a string with a plus sign"
        :samples ["+0.82" "+35." "+35" "+.72"])
      (dt-test-cases/false-case "any decimal as a string with a minus sign"
        :samples ["-0.82" "-35." "-35" "-.72"])
      (dt-test-cases/false-case "zero"
        :samples ["0" "-0" "+0" "0." "-0." "+0."
                  "0.00" "-0.0000" "+0.000"])
      (dt-test-cases/false-case "any non-base 10 integer as a string"
        :samples ["0xff" "035" "2r1011" "7N" "36rCRAZY"])
      (dt-test-cases/false-case "many zeroes"
        :samples ["00", "0000000"])
      (dt-test-cases/false-case "leading zeroes"
        :samples ["00123" "00.123" "000.456"])
      (dt-test-cases/false-case "multiple leading signs"
        :samples ["+-123.4" "-+45" "--0.23" "++10.0" "+-+23.456"])
      (dt-test-cases/false-case "any integer"
        :samples [0 35 -35
                  0xff 017 2r1011 7N 36rCRAZY
                  Integer/MIN_VALUE
                  Integer/MAX_VALUE])
      (dt-test-cases/false-case "a floating point number" :sample 35.23)
      (dt-test-cases/false-case "a non-numeric string"
        :sample "not-a-decimal")
      (dt-test-cases/false-case "a non-string"
        :samples [true false #{1 2 3}])
      (dt-test-cases/false-case "an empty string"
        :sample "")
      (dt-test-cases/false-case "nil" :sample nil)))

  (testing "locale specific cases"
    (dt-test-cases/assert-cases-satisfied-by dt-number/decimal-string?
      (dt-test-cases/true-case
        "a decimal string with correct thousands separator for the UK"
        :samples ["1,000.456" "10,000" "100,000.1" "1,000,000."]
        :locale (Locale/UK))
      (dt-test-cases/false-case
        "a decimal string with incorrect thousands separator for the UK"
        :samples ["1.000,56" "10.000," "100.000,1" "1.000.000,"]
        :locale (Locale/UK))
      (dt-test-cases/true-case
        "a decimal string with correct thousands separator for Germany"
        :samples ["1.000,123" "10.000" "100.000,4" "1.000.000,"]
        :locale (Locale/GERMANY))
      (dt-test-cases/false-case
        "a decimal string with incorrect thousands separator for Germany"
        :samples ["1,000.456" "10,000.0" "100,000.1" "1,000,000."]
        :locale (Locale/GERMANY))
      (dt-test-cases/true-case
        "a decimal string with correct thousands separator for France"
        :samples ["1 000,456"
                  "10 000,0"
                  "100 000,1"
                  "1 000 000,"]
        :locale (Locale/FRANCE))
      (dt-test-cases/false-case
        "a decimal string with incorrect thousands separator for France"
        :samples ["1,000.123" "10.000,0" "100,000.4" "1.000.000,"]
        :locale (Locale/FRANCE))
      (dt-test-cases/true-case
        "a decimal string with thousands separator in the wrong place"
        :samples ["1,0.1" "1,00,0000."]
        :locale (Locale/UK)
        :note "TODO: This is treated as valid but shouldn't be..."))))

(deftest negative-decimal-string?
  (testing "general cases"
    (dt-test-cases/assert-cases-satisfied-by
      dt-number/negative-decimal-string?
      (dt-test-cases/true-case "any decimal as a string with a minus sign"
        :samples ["-0.82" "-35." "-35" "-.72"])
      (dt-test-cases/false-case "any positive decimal as a string with no sign"
        :samples ["0.82" "35." "35" "2.78" ".91"])
      (dt-test-cases/false-case "any decimal as a string with a plus sign"
        :samples ["+0.82" "+35." "+35" "+.72"])
      (dt-test-cases/false-case "zero"
        :samples ["0" "-0" "+0" "0." "-0." "+0."
                  "0.00" "-0.0000" "+0.000"])
      (dt-test-cases/false-case "any non-base 10 integer as a string"
        :samples ["0xff" "035" "2r1011" "7N" "36rCRAZY"])
      (dt-test-cases/false-case "many zeroes"
        :samples ["00", "0000000"])
      (dt-test-cases/false-case "leading zeroes"
        :samples ["00123" "00.123" "000.456"])
      (dt-test-cases/false-case "multiple leading signs"
        :samples ["+-123.4" "-+45" "--0.23" "++10.0" "+-+23.456"])
      (dt-test-cases/false-case "any integer"
        :samples [0 35 -35
                  0xff 017 2r1011 7N 36rCRAZY
                  Integer/MIN_VALUE
                  Integer/MAX_VALUE])
      (dt-test-cases/false-case "a floating point number" :sample 35.23)
      (dt-test-cases/false-case "a non-numeric string"
        :sample "not-a-decimal")
      (dt-test-cases/false-case "a non-string"
        :samples [true false #{1 2 3}])
      (dt-test-cases/false-case "an empty string"
        :sample "")
      (dt-test-cases/false-case "nil" :sample nil)))

  (testing "locale specific cases"
    (dt-test-cases/assert-cases-satisfied-by dt-number/decimal-string?
      (dt-test-cases/true-case
        "a decimal string with correct thousands separator for the UK"
        :samples ["-1,000.456" "-10,000" "-100,000.1" "-1,000,000."]
        :locale (Locale/UK))
      (dt-test-cases/false-case
        "a decimal string with incorrect thousands separator for the UK"
        :samples ["-1.000,56" "-10.000," "-100.000,1" "-1.000.000,"]
        :locale (Locale/UK))
      (dt-test-cases/true-case
        "a decimal string with correct thousands separator for Germany"
        :samples ["-1.000,123" "-10.000" "-100.000,4" "-1.000.000,"]
        :locale (Locale/GERMANY))
      (dt-test-cases/false-case
        "a decimal string with incorrect thousands separator for Germany"
        :samples ["-1,000.456" "-10,000.0" "-100,000.1" "-1,000,000."]
        :locale (Locale/GERMANY))
      (dt-test-cases/true-case
        "a decimal string with correct thousands separator for France"
        :samples ["-1 000,456"
                  "-10 000,0"
                  "-100 000,1"
                  "-1 000 000,"]
        :locale (Locale/FRANCE))
      (dt-test-cases/false-case
        "a decimal string with incorrect thousands separator for France"
        :samples ["-1,000.123" "-10.000,0" "-100,000.4" "-1.000.000,"]
        :locale (Locale/FRANCE))
      (dt-test-cases/true-case
        "a decimal string with thousands separator in the wrong place"
        :samples ["-1,0.1" "-1,00,0000."]
        :locale (Locale/UK)
        :note "TODO: This is treated as valid but shouldn't be..."))))
