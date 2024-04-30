(ns datatype.number.core
  (:refer-clojure :exclude [zero?])
  (:require
   [clojure.string :as string]

   [datatype.support :as dts]

   [icu4clj.text.number-format :as icu-tnf]
   [icu4clj.text.unicode-characters :as icu-tuc]))

; int?
; float?
; double?
; decimal?

; natural-number?
; natural-number-string?

; zero-string?

(defn- string-exactly-matching? [value pattern]
  (cond
    (nil? value) false
    (not (string? value)) false
    :else
    (dts/exception->false
      (dts/re-satisfies?
        (dts/re-exact-pattern pattern)
        value))))

(defn zero-digit []
  (:zero-digit (icu-tnf/decimal-format-symbols)))

(defn all-digits []
  (:digits (icu-tnf/decimal-format-symbols)))

(defn plus-sign []
  (:plus-sign (icu-tnf/decimal-format-symbols)))

(defn minus-sign []
  (:minus-sign (icu-tnf/decimal-format-symbols)))

(defn non-zero-digits []
  (let [{:keys [digits zero-digit]} (icu-tnf/decimal-format-symbols)]
    (remove #(= zero-digit %) digits)))

(defn decimal-separator []
  (:decimal-separator (icu-tnf/decimal-format-symbols)))

(defn grouping-separator []
  (:grouping-separator (icu-tnf/decimal-format-symbols)))

(defn plus-sign-pattern []
  (dts/re-quote (plus-sign)))

(defn minus-sign-pattern []
  (dts/re-quote (minus-sign)))

(defn grouping-separator-pattern []
  (let [grouping-separator (grouping-separator)]
    (if (icu-tuc/space-separator-character? grouping-separator)
      "[\\p{Zs}]"
      (str "[" grouping-separator "]"))))

(defn decimal-separator-pattern []
  (dts/re-quote (decimal-separator)))

(defn optional-sign-pattern []
  (str "(" (plus-sign-pattern) "|" (minus-sign-pattern) ")?"))

(defn optional-plus-sign-pattern []
  (str "(" (plus-sign-pattern) ")?"))

(defn zero-digit-pattern []
  (zero-digit))

(defn non-zero-digits-pattern []
  (str "[" (string/join (non-zero-digits)) "]"))

(defn all-digits-pattern []
  (str "[" (string/join (all-digits)) "]"))

(defn denary-string-pattern
  ([] (denary-string-pattern {}))
  ([{:keys [sign-pattern allow-zero? allow-fractional-part?]
     :or   {sign-pattern           (optional-sign-pattern)
            allow-zero?            true
            allow-fractional-part? true}}]
   (let [decimal-separator-pattern (decimal-separator-pattern)
         grouping-separator-pattern (grouping-separator-pattern)

         zero-digit-pattern (zero-digit-pattern)
         all-digits-pattern (all-digits-pattern)
         non-zero-digits-pattern (non-zero-digits-pattern)

         any-fractional-part-pattern (str all-digits-pattern "*")
         zero-fractional-part-pattern (str zero-digit-pattern "*")
         non-zero-fractional-part-pattern
         (str
           zero-digit-pattern "*"
           non-zero-digits-pattern "+"
           zero-digit-pattern "*")

         integral-pattern
         (cond->
          (str
            non-zero-digits-pattern
            "(" all-digits-pattern "|" grouping-separator-pattern ")*")
           allow-fractional-part?
           (str decimal-separator-pattern "?"))
         fractional-pattern
         (str zero-digit-pattern "?" decimal-separator-pattern
           non-zero-fractional-part-pattern)
         decimal-pattern
         (str integral-pattern any-fractional-part-pattern)

         zero-pattern
         (cond-> zero-digit-pattern
           allow-fractional-part?
           (str "|"
             zero-digit-pattern decimal-separator-pattern
             zero-fractional-part-pattern))
         non-zero-pattern
         (cond-> integral-pattern
           allow-fractional-part?
           (str "|" decimal-pattern "|" fractional-pattern))
         number-part-pattern
         (cond-> non-zero-pattern
           allow-zero?
           (str "|" "(" zero-pattern ")"))]
     (re-pattern
       (str sign-pattern "(" number-part-pattern ")")))))

(defn positive?
  "Returns true if the provided value is a positive number, else returns false."
  [value]
  (dts/exception->false
    (clojure.core/pos? value)))

(defn negative?
  "Returns true if the provided value is a negative number, else returns false."
  [value]
  (dts/exception->false
    (clojure.core/neg? value)))

(defn zero?
  "Returns true if the provided value is zero, else returns false."
  [value]
  (dts/exception->false
    (clojure.core/zero? value)))

(defn integer-string?
  "Returns true if the provided value is a string representing a base 10
  integer, else returns false."
  [value]
  (string-exactly-matching? value
    (denary-string-pattern
      {:allow-fractional-part? false})))

(defn positive-integer-string?
  "Returns true if the provided value is a string representing a positive
  base 10 integer, else returns false."
  [value]
  (string-exactly-matching? value
    (denary-string-pattern
      {:sign-pattern           (optional-plus-sign-pattern)
       :allow-zero?            false
       :allow-fractional-part? false})))

(defn negative-integer-string?
  "Returns true if the provided value is a string representing a negative
  base 10 integer, else returns false."
  [value]
  (string-exactly-matching? value
    (denary-string-pattern
      {:sign-pattern           (minus-sign-pattern)
       :allow-zero?            false
       :allow-fractional-part? false})))

(defn decimal-string?
  "Returns true if the provided value is a string representing a decimal number,
  else returns false."
  [value]
  (string-exactly-matching? value
    (denary-string-pattern)))

(defn positive-decimal-string?
  "Returns true if the provided value is a string representing a positive
  decimal number, else returns false"
  [value]
  (string-exactly-matching? value
    (denary-string-pattern
      {:sign-pattern (optional-plus-sign-pattern)
       :allow-zero?  false})))

(defn negative-decimal-string?
  "Returns true if the provided value is a string representing a negative
  decimal number, else returns false"
  [value]
  (string-exactly-matching? value
    (denary-string-pattern
      {:sign-pattern (minus-sign-pattern)
       :allow-zero?  false})))
