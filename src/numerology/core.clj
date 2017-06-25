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
  5)

(defmethod replace-idx :default [idx ns]
  (nth ns idx))

(defn process [ns]
  (flatten (map-indexed replace-idx (repeat (count ns) ns))))
