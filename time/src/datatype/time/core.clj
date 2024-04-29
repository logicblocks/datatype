(ns datatype.time.core
  (:require
   [cljc.java-time.offset-date-time :as jt-odt]

   [datatype.support :as dts]))

; iso8601-duration-string?
; iso8601-interval-string?
; iso8601-local-date-string?
; iso8601-local-time-string?
; iso8601-local-datetime-string?

; past-iso8601-local-date-string?
; past-iso8601-local-datetime-string?
; past-iso8601-zoned-datetime-string?

; future-iso8601-local-date-string?
; future-iso8601-local-datetime-string?
; future-iso8601-zoned-datetime-string?

(defn iso8601-zoned-datetime-string?
  "Returns true if the provided value is a string representing an ISO8601
  zoned datetime, else returns false."
  [value]
  (dts/exception->false
    (boolean (jt-odt/parse value))))
