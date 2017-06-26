(ns numerology.core)

(defmulti replace-idx (fn [idx ns] (nth ns idx)))

(defmethod replace-idx 9 [idx ns]
  [10 10])

(defmethod replace-idx 2 [idx ns]
  (let [left (nth ns (dec idx))]
    (repeat left 1)))

(defmethod replace-idx 6 [idx ns]
  (let [left (nth ns (dec idx))
        repetition (nth ns (+ idx left))]
    (repeat repetition 3)))

(defmethod replace-idx 3 [idx ns]
  (if (> (dec (count ns)) idx)
    (let [right (nth ns (inc idx))]
      (if (= right 5)
        3
        5))
    5))

(defmethod replace-idx 4 [idx ns]
  (if (> idx 0)
    (let [left (nth ns (dec idx))]
      (if (= left 5)
        4
        3))
    3))

(defmethod replace-idx :default [idx ns]
  (nth ns idx))

(defn process [ns]
  (flatten
    (:out
      (reduce
        #(update-in %1 [:out] conj (apply replace-idx %2))
        {:replacement-counts {} :out []}
        (map-indexed vector (repeat (count ns) ns))))))
