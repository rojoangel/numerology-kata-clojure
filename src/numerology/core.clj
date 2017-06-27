(ns numerology.core)

(defmulti replace-idx (fn [idx ns _] (nth ns idx)))

(defmethod replace-idx 9 [idx ns _]
  {:replacement [10 10] :count-diff 0})

(defmethod replace-idx 2 [idx ns _]
  (let [left (nth ns (dec idx))]
    {:replacement (repeat left 1) :count-diff 0}))

(defmethod replace-idx 6 [idx ns _]
  (let [left (nth ns (dec idx))
        repetition (nth ns (+ idx left))]
    {:replacement (repeat repetition 3) :count-diff 0}))

(defmethod replace-idx 3 [idx ns replacement-counts]
  (if (even? replacement-counts)
    (if (> (dec (count ns)) idx)
      (let [right (nth ns (inc idx))]
        (if (= right 5)
          {:replacement 3 :count-diff 0}
          {:replacement 5 :count-diff 1}))
      {:replacement 5 :count-diff 0})
    {:replacement 3 :count-diff 0}))

(defmethod replace-idx 4 [idx ns _]
  (if (> idx 0)
    (let [left (nth ns (dec idx))]
      (if (= left 5)
        {:replacement 4 :count-diff 0}
        {:replacement 3 :count-diff 0}))
    {:replacement 3 :count-diff 0}))

(defmethod replace-idx :default [idx ns _]
  {:replacement (nth ns idx) :count-diff 0})

(defn update-acc [acc [idx ns]]
  (let [replacement-result (replace-idx idx ns (:replacement-counts acc))]
    (update-in
      (update-in acc [:out] conj (:replacement replacement-result))
      [:replacement-counts] + (:count-diff replacement-result))))

(defn process [ns]
  (flatten
    (:out
      (reduce
        update-acc
        {:replacement-counts 0 :out []}
        (map-indexed vector (repeat (count ns) ns))))))
