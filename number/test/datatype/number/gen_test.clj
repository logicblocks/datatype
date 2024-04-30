(ns datatype.number.gen-test
  (:require
   [clojure.spec.gen.alpha :as gen]
   [clojure.test :refer [deftest testing is]]

   [datatype.support :as dts]
   [datatype.number.gen :as dt-number-gen]))

(deftest gen-positive-number
  (testing "does not generate nil"
    (is (every? false?
          (map nil?
            (gen/sample (dt-number-gen/gen-positive-number) 100)))))
  (testing "generates positive numbers"
    (is (every? true?
          (map #(clojure.core/pos? %)
            (gen/sample (dt-number-gen/gen-positive-number)))))))

(deftest gen-negative-number
  (testing "does not generate nil"
    (is (every? false?
          (map nil?
            (gen/sample (dt-number-gen/gen-negative-number) 100)))))
  (testing "generates negative numbers"
    (is (every? true?
          (map #(clojure.core/neg? %)
            (gen/sample (dt-number-gen/gen-negative-number)))))))

(deftest gen-zero-number
  (testing "does not generate nil"
    (is (every? false?
          (map nil?
            (gen/sample (dt-number-gen/gen-zero-number) 100)))))
  (testing "generates zero numbers"
    (is (every? true?
          (map #(= 0 %)
            (gen/sample (dt-number-gen/gen-zero-number)))))))

(deftest gen-integer-string
  (testing "does not generate nil"
    (is (every? false?
          (map nil?
            (gen/sample (dt-number-gen/gen-integer-string) 100)))))

  (testing "does not generate empty string"
    (is (every? false?
          (map #(= "" %)
            (gen/sample (dt-number-gen/gen-integer-string) 100)))))

  (testing "generates integer strings"
    (let [samples (gen/sample (dt-number-gen/gen-integer-string))
          pred #(or
                  (dts/re-satisfies? #"^[-+]?0$" %)
                  (dts/re-satisfies? #"^[-+]?[1-9]\d*$" %))]
      (is (every? true? (map pred samples))
        (str "mismatching samples: "
          (filterv #(not (pred %)) samples)))))

  (testing "sometimes generates zero"
    (is (some #(= "0" %)
          (gen/sample (dt-number-gen/gen-integer-string) 100)))))

(deftest gen-positive-integer-string
  (testing "does not generate nil"
    (is (every? false?
          (map nil?
            (gen/sample (dt-number-gen/gen-positive-integer-string) 100)))))

  (testing "does not generate empty string"
    (is (every? false?
          (map #(= "" %)
            (gen/sample (dt-number-gen/gen-positive-integer-string) 100)))))

  (testing "generates positive integer strings"
    (let [samples (gen/sample (dt-number-gen/gen-positive-integer-string))
          pred #(dts/re-satisfies? #"^[+]?[1-9]\d*$" %)]
      (is (every? true? (map pred samples))
        (str "mismatching samples: "
          (filterv #(not (pred %)) samples))))))

(deftest gen-negative-integer-string
  (testing "does not generate nil"
    (is (every? false?
          (map nil?
            (gen/sample (dt-number-gen/gen-negative-integer-string) 100)))))

  (testing "does not generate empty string"
    (is (every? false?
          (map #(= "" %)
            (gen/sample (dt-number-gen/gen-negative-integer-string) 100)))))

  (testing "generates negative integer strings"
    (let [samples (gen/sample (dt-number-gen/gen-negative-integer-string))
          pred #(dts/re-satisfies? #"^-[1-9]\d*$" %)]
      (is (every? true? (map pred samples))
        (str "mismatching samples: "
          (filterv #(not (pred %)) samples))))))

(deftest gen-decimal-string
  (testing "does not generate nil"
    (is (every? false?
          (map nil?
            (gen/sample (dt-number-gen/gen-decimal-string) 100)))))

  (testing "does not generate empty string"
    (is (every? false?
          (map #(= "" %)
            (gen/sample (dt-number-gen/gen-decimal-string) 100)))))

  (testing "generates decimal strings"
    (let [samples (gen/sample (dt-number-gen/gen-decimal-string))
          can-parse?
          (fn [x]
            (try
              (boolean (parse-double x))
              (catch Exception _ false)))]
      (is (every? true? (map can-parse? samples))
        (str "mismatching samples: "
          (filterv #(not (can-parse? %)) samples))))))

(deftest gen-positive-decimal-string
  (testing "does not generate nil"
    (is (every? false?
          (map nil?
            (gen/sample (dt-number-gen/gen-positive-decimal-string) 100)))))

  (testing "does not generate empty string"
    (is (every? false?
          (map #(= "" %)
            (gen/sample (dt-number-gen/gen-positive-decimal-string) 100)))))

  (testing "generates positive decimal strings"
    (let [samples (gen/sample (dt-number-gen/gen-positive-decimal-string))
          positive-floating-point?
          (fn [x]
            (try
              (pos? (parse-double x))
              (catch Exception _ false)))]
      (is (every? true? (map positive-floating-point? samples))
        (str "mismatching samples: "
          (filterv #(not (positive-floating-point? %)) samples))))))

(deftest gen-negative-decimal-string
  (testing "does not generate nil"
    (is (every? false?
          (map nil?
            (gen/sample (dt-number-gen/gen-negative-decimal-string) 100)))))

  (testing "does not generate empty string"
    (is (every? false?
          (map #(= "" %)
            (gen/sample (dt-number-gen/gen-negative-decimal-string) 100)))))

  (testing "generates negative decimal strings"
    (let [samples (gen/sample (dt-number-gen/gen-negative-decimal-string))
          negative-floating-point?
          (fn [x]
            (try
              (neg? (parse-double x))
              (catch Exception _ false)))]
      (is (every? true? (map negative-floating-point? samples))
        (str "mismatching samples: "
          (filterv #(not (negative-floating-point? %)) samples))))))
