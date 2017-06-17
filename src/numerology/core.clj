(ns numerology.core)

(defn replace [n]
  (if (= 9 n)
    [10 10]
    n))

(defn process [ns]
  (flatten (map replace ns)))
