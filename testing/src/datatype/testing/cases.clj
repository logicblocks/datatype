(ns datatype.testing.cases
  (:require
   [clojure.test :refer [testing is]])
  (:import
   [java.util Locale]))

(defn- coll-from-single-or-plural [m singular plural]
  (if (contains? m singular)
    [(singular m)]
    (plural m)))

(defn- test-case [satisfied? title options]
  {:satisfied? satisfied?
   :title      title
   :samples    (coll-from-single-or-plural options :sample :samples)
   :locale     (get options :locale (Locale/UK))
   :notes      (coll-from-single-or-plural options :note :notes)})

(defmacro with-locale [^Locale locale & body]
  `(let [default-locale# (Locale/getDefault)]
     (try
       (Locale/setDefault ~locale)
       ~@body
       (finally
         (Locale/setDefault default-locale#)))))

(defn true-case [title & {:as options}]
  (test-case true title options))

(defn false-case [title & {:as options}]
  (test-case false title options))

(defn satisfies-samples? [predicate case]
  (every?
    (fn [sample] (= sample (:satisfied? case)))
    (map predicate (:samples case))))

(defn unsatisfied-samples [predicate case]
  (set (filter (fn [sample] (not= (predicate sample) (:satisfied? case))) (:samples case))))

(defmacro assert-cases-satisfied-by [predicate & cases]
  `(doseq [case# ~(vec cases)]
     (testing (str "for " (:title case#))
       (with-locale (:locale case#)
         (is (satisfies-samples? ~predicate case#)
           (str "unsatisfied for: "
             (unsatisfied-samples ~predicate case#)))))))
