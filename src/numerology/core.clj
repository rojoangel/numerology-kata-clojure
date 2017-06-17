(ns numerology.core)

(defn replace [idx ns]
  (let [n (nth ns idx)]
    (case n
      9 [10 10]
      2 (let [left (nth ns (dec idx))]
          (repeat left 1) )
      n)))

(defn process [ns]
  (flatten (map-indexed replace (repeat (count ns) ns))))
