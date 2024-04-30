(ns datatype.number.gen
  (:require
   [clojure.spec.gen.alpha :as gen]
   [clojure.string :as string]

   [datatype.number.core :as dt-number]))

(defn gen-positive-number []
  (gen/gen-for-pred pos-int?))

(defn gen-negative-number []
  (gen/gen-for-pred neg-int?))

(defn gen-zero-number []
  (gen/gen-for-pred zero?))

(defn gen-denary-string
  ([] (gen-denary-string {}))
  ([{:keys [signs allow-no-sign? allow-zero? include-fractional-part?]
     :or   {signs                    [(dt-number/plus-sign)
                                      (dt-number/minus-sign)]
            allow-no-sign?           true
            allow-zero?              true
            include-fractional-part? true}}]
   (let [zero-digit (dt-number/zero-digit)
         all-digits (dt-number/all-digits)
         non-zero-digits (dt-number/non-zero-digits)
         decimal-separator (dt-number/decimal-separator)]
     (gen/fmap
       (fn [[sign integer fraction
             include-integer? include-fraction? corrector]]
         (let [include-neither?
               (and
                 (not include-integer?)
                 (not include-fraction?))
               include-integer?
               (if include-neither? (= corrector :integer) include-integer?)
               include-fraction?
               (if include-neither? (= corrector :fraction) include-fraction?)

               integer
               (if (or include-integer? (not include-fractional-part?))
                 (string/join integer)
                 "")
               fraction
               (if (and include-fraction? include-fractional-part?)
                 (string/join fraction)
                 "")]
           (cond->
            (str sign integer)
             include-fractional-part? (str decimal-separator fraction))))
       (gen/tuple
         (gen/elements (if allow-no-sign? (conj signs "") signs))
         (gen/frequency
           [[(if allow-zero? 1 0)
             (gen/return zero-digit)]
            [19
             (gen/fmap
               (fn [[first-digit other-digits]]
                 (str first-digit (string/join other-digits)))
               (gen/tuple
                 (gen/elements non-zero-digits)
                 (gen/vector (gen/elements all-digits))))]])
         (gen/frequency
           [[(if allow-zero? 1 0)
             (gen/vector (gen/elements all-digits) 1 100)]
            [19
             (gen/fmap
               (fn [[pre-digits non-zero-digit post-digits]]
                 (str
                   (string/join pre-digits)
                   non-zero-digit
                   (string/join post-digits)))
               (gen/tuple
                 (gen/vector (gen/elements all-digits))
                 (gen/elements non-zero-digits)
                 (gen/vector (gen/elements all-digits))))]])
         (gen/boolean)
         (gen/boolean)
         (gen/elements [:integer :fraction]))))))

(defn gen-integer-string []
  (gen-denary-string
    {:signs                    [(dt-number/plus-sign) (dt-number/minus-sign)]
     :include-fractional-part? false}))

(defn gen-positive-integer-string []
  (gen-denary-string
    {:signs                    [(dt-number/plus-sign)]
     :allow-zero?              false
     :include-fractional-part? false}))

(defn gen-negative-integer-string []
  (gen-denary-string
    {:signs                    [(dt-number/minus-sign)]
     :allow-no-sign?           false
     :allow-zero?              false
     :include-fractional-part? false}))

(defn gen-decimal-string []
  (gen-denary-string
    {:signs [(dt-number/plus-sign) (dt-number/minus-sign)]}))

(defn gen-positive-decimal-string []
  (gen-denary-string
    {:signs       [(dt-number/plus-sign)]
     :allow-zero? false}))

(defn gen-negative-decimal-string []
  (gen-denary-string
    {:signs          [(dt-number/minus-sign)]
     :allow-no-sign? false
     :allow-zero?    false}))
