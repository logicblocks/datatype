(ns datatype.string.gen-test
  (:require
    [clojure.spec.gen.alpha :as gen]
    [clojure.test :refer [deftest testing is]]

    [datatype.support :as dts]
    [datatype.string.gen :as dt-string-gen]

    [icu4clj.text.unicode-characters :as icu-tuc]
    [icu4clj.text.string :as icu-ts]))

(add-tap prn)

(remove-tap prn)

(deftest gen-string-unicode
  (testing "by default"
    (testing "generates some empty strings"
      (let [samples
            (gen/sample (dt-string-gen/gen-string-unicode "[a-z]") 100)]
        (is (not (empty?
                   (filter
                     (fn [sample] (= 0 (count sample)))
                     samples))))))
    (testing "generates strings of varying lengths"
      (let [samples
            (gen/sample (dt-string-gen/gen-string-unicode "[a-z]") 100)
            lengths
            (into #{} (map count samples))]
        (is (> (count lengths) 20)))))
  (testing "when :allow-empty? is false"
    (testing "generates no empty strings"
      (let [samples
            (gen/sample
              (dt-string-gen/gen-string-unicode "[a-z]"
                {:allow-empty? false})
              100)]
        (is (empty?
              (filter
                (fn [sample] (= 0 (count sample)))
                samples))))))
  (testing "when :length is specified"
    (testing "generates strings of the specified length"
      (let [length 12
            samples
            (gen/sample
              (dt-string-gen/gen-string-unicode "[a-z]"
                {:length length})
              100)]
        (is (every?
              (fn [sample] (= (count sample) length))
              samples)))))
  (testing "when only :min-length specified"
    (testing "generates strings of at least the specified minimum length"
      (let [min-length 5
            samples
            (gen/sample
              (dt-string-gen/gen-string-unicode "[a-z]"
                {:min-length min-length})
              100)
            lengths
            (into #{} (map count samples))]
        (is (every?
              (fn [length] (>= length min-length))
              lengths)))))
  (testing "when only :max-length specified"
    (testing "generates strings of at most the specified maximum length"
      (let [max-length 15
            samples
            (gen/sample
              (dt-string-gen/gen-string-unicode "[a-z]"
                {:max-length max-length})
              100)
            lengths
            (into #{} (map count samples))]
        (is (every?
              (fn [length] (<= length max-length))
              lengths)))))
  (testing "when both :min-length and :max-length specified"
    (testing
      "generates strings of between the specified minimum and maximum lengths"
      (let [min-length 5
            max-length 15
            samples
            (gen/sample
              (dt-string-gen/gen-string-unicode "[a-z]"
                {:min-length min-length
                 :max-length max-length})
              100)
            lengths
            (into #{} (map count samples))]
        (is (every?
              (fn [length]
                (and
                  (<= length max-length)
                  (>= length min-length)))
              lengths)))))
  (testing "throws when :min-length, :max-length and :length specified"
    (let [min-length 5
          max-length 15
          length 10
          options
          {:length     length
           :min-length min-length
           :max-length max-length}
          exception
          (try
            (gen/generate
              (dt-string-gen/gen-string-unicode "[a-z]" options))
            nil
            (catch Exception e
              e))]
      (is (= (ex-message exception)
            (str
              "Ambiguous options, :length can't be specified at the "
              "same time as :min-length or :max-length.")))
      (is (= (ex-data exception) options)))))

(deftest gen-string-whitespace
  (testing "generates some empty strings"
    (let [samples (gen/sample (dt-string-gen/gen-string-whitespace) 100)]
      (is (not (empty?
                 (filter
                   (fn [sample] (= 0 (count sample)))
                   samples))))))

  (testing "generates only whitespace strings"
    (let [samples (gen/sample (dt-string-gen/gen-string-whitespace) 100)]
      (is (every? true?
            (map
              (fn [sample]
                (every?
                  icu-tuc/whitespace-character?
                  (icu-ts/codepoint-seq sample)))
              samples))))))

(deftest gen-string-non-whitespace
  (testing "generates no empty strings when requested"
    (let [samples (gen/sample
                    (dt-string-gen/gen-string-non-whitespace
                      {:allow-empty? false})
                    100)]
      (is (empty?
            (filter
              (fn [sample] (= 0 (count sample)))
              samples)))))

  (testing "generates only non-whitespace strings"
    (let [samples (gen/sample
                    (dt-string-gen/gen-string-non-whitespace
                      {:allow-empty? false})
                    100)]
      (is (every? true?
            (map
              (fn [sample]
                (some
                  icu-tuc/non-whitespace-character?
                  (icu-ts/codepoint-seq sample)))
              samples))))))

(deftest gen-string-ascii-digits
  (testing "generates no empty strings when requested"
    (is (empty?
          (filter
            (fn [sample] (= 0 (count sample)))
            (gen/sample
              (dt-string-gen/gen-string-ascii-digits
                {:allow-empty? false})
              100)))))

  (testing "generates only ASCII digit strings"
    (is (every? true?
          (map #(dts/re-satisfies? #"^\d+$" %)
            (gen/sample
              (dt-string-gen/gen-string-ascii-digits
                {:allow-empty? false})
              100))))))

(deftest gen-string-lowercase-ascii-alphabetics
  (testing "generates no empty strings when requested"
    (is (empty?
          (filter
            (fn [sample] (= 0 (count sample)))
            (gen/sample
              (dt-string-gen/gen-string-lowercase-ascii-alphabetics
                {:allow-empty? false})
              100)))))

  (testing "generates only lowercase ASCII alphabetic strings"
    (is (every? true?
          (map #(dts/re-satisfies? #"^[a-z]+$" %)
            (gen/sample
              (dt-string-gen/gen-string-lowercase-ascii-alphabetics
                {:allow-empty? false})
              100))))))

(deftest gen-string-uppercase-ascii-alphabetics
  (testing "generates no empty strings when requested"
    (is (empty?
          (filter
            (fn [sample] (= 0 (count sample)))
            (gen/sample
              (dt-string-gen/gen-string-uppercase-ascii-alphabetics
                {:allow-empty? false})
              100)))))

  (testing "generates only uppercase ASCII alphabetic strings"
    (is (every? true?
          (map #(dts/re-satisfies? #"^[A-Z]+$" %)
            (gen/sample
              (dt-string-gen/gen-string-uppercase-ascii-alphabetics
                {:allow-empty? false})
              100))))))

(deftest gen-string-ascii-alphabetics
  (testing "generates no empty strings when requested"
    (is (empty?
          (filter
            (fn [sample] (= 0 (count sample)))
            (gen/sample
              (dt-string-gen/gen-string-ascii-alphabetics
                {:allow-empty? false})
              100)))))

  (testing "generates only ASCII alphabetic strings"
    (is (every? true?
          (map #(dts/re-satisfies? #"^[a-zA-Z]+$" %)
            (gen/sample
              (dt-string-gen/gen-string-ascii-alphabetics
                {:allow-empty? false})
              100))))))

(deftest gen-string-lowercase-ascii-alphanumerics
  (testing "generates no empty strings when requested"
    (is (empty?
          (filter
            (fn [sample] (= 0 (count sample)))
            (gen/sample
              (dt-string-gen/gen-string-lowercase-ascii-alphanumerics
                {:allow-empty? false})
              100)))))

  (testing "generates only lowercase ASCII alphanumeric strings"
    (is (every? true?
          (map #(dts/re-satisfies? #"^[a-z0-9]+$" %)
            (gen/sample
              (dt-string-gen/gen-string-lowercase-ascii-alphanumerics
                {:allow-empty? false})
              100))))))

(deftest gen-string-uppercase-ascii-alphanumerics
  (testing "generates no empty strings when requested"
    (is (empty?
          (filter
            (fn [sample] (= 0 (count sample)))
            (gen/sample
              (dt-string-gen/gen-string-uppercase-ascii-alphanumerics
                {:allow-empty? false})
              100)))))

  (testing "generates only uppercase ASCII alphanumeric strings"
    (is (every? true?
          (map #(dts/re-satisfies? #"^[A-Z0-9]+$" %)
            (gen/sample
              (dt-string-gen/gen-string-uppercase-ascii-alphanumerics
                {:allow-empty? false})
              100))))))

(deftest gen-string-ascii-alphanumerics
  (testing "generates no empty strings when requested"
    (is (empty?
          (filter
            (fn [sample] (= 0 (count sample)))
            (gen/sample
              (dt-string-gen/gen-string-ascii-alphanumerics
                {:allow-empty? false})
              100)))))

  (testing "generates only ASCII alphabetic strings"
    (is (every? true?
          (map #(dts/re-satisfies? #"^[a-zA-Z0-9]+$" %)
            (gen/sample
              (dt-string-gen/gen-string-ascii-alphanumerics
                {:allow-empty? false})
              100))))))
