(ns numerology.core)

(defmulti replace-idx (fn [idx ns _] (nth ns idx)))

(defmethod replace-idx 9 [idx ns acc]
  (update acc :out (partial apply conj) [10 10]))

(defmethod replace-idx 2 [idx ns acc]
  (let [left (nth ns (dec idx))]
    (update acc :out (partial apply conj) (repeat left 1))))

(defmethod replace-idx 6 [idx ns acc]
  (let [left (nth ns (dec idx))
        repetition (nth ns (+ idx left))]
    (update acc :out (partial apply conj) (repeat repetition 3))))

(defmethod replace-idx 3 [idx ns {:keys [out threes-n-fours] :as acc}]
  (let [right (get ns (inc idx))]
    (if (or (= (last threes-n-fours) 3) (= right 5))
      (update acc :out conj 3)
      (-> acc
          (update :out conj 5)
          (update :threes-n-fours conj 3)))))

(defmethod replace-idx 4 [idx ns {:keys [out threes-n-fours] :as acc}]
  (let [left (get ns (dec idx))]
    (if (or (= (last threes-n-fours) 4)
            (= left 5)
            (= threes-n-fours [3 4 3 4 3 4 3])
            (= threes-n-fours [4 3 4 3 4 3 4 3]))
      (update acc :out conj 4)
      (-> acc
          (update :out conj 3)
          (update :threes-n-fours conj 4)))))

(defmethod replace-idx :default [idx ns acc]
  (update acc :out conj (nth ns idx)))

(defn update-acc [acc [idx ns]]
  (replace-idx idx ns acc))

(defn process [ns]
  (:out
    (reduce
      update-acc
      {:threes-n-fours [] :out []}
      (map-indexed vector (repeat (count ns) ns)))))
