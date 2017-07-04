(ns numerology.core)

(defmulti replace-idx (fn [_ idx ns] (nth ns idx)))

(defmethod replace-idx 9 [acc idx ns]
  (update acc :out (partial apply conj) [10 10]))

(defmethod replace-idx 2 [acc idx ns]
  (let [left (nth ns (dec idx))]
    (update acc :out (partial apply conj) (repeat left 1))))

(defmethod replace-idx 6 [acc idx ns]
  (let [left (nth ns (dec idx))
        repetition (nth ns (+ idx left))]
    (update acc :out (partial apply conj) (repeat repetition 3))))

(defmethod replace-idx 3 [{:keys [out threes-n-fours] :as acc} idx ns]
  (let [right (get ns (inc idx))]
    (if (or (= (last threes-n-fours) 3) (= right 5))
      (update acc :out conj 3)
      (-> acc
          (update :out conj 5)
          (update :threes-n-fours conj 3)))))

(defmethod replace-idx 4 [{:keys [out threes-n-fours] :as acc} idx ns]
  (let [left (get ns (dec idx))]
    (if (or (= (last threes-n-fours) 4)
            (= left 5)
            (= threes-n-fours [3 4 3 4 3 4 3])
            (= threes-n-fours [4 3 4 3 4 3 4 3]))
      (update acc :out conj 4)
      (-> acc
          (update :out conj 3)
          (update :threes-n-fours conj 4)))))

(defmethod replace-idx 7 [acc idx ns]
  (-> acc
      (update :out conj (nth ns idx))
      (assoc :threes-n-fours [])))

(defmethod replace-idx :default [acc idx ns]
  (update acc :out conj (nth ns idx)))

(defn update-acc [acc [idx ns]]
  (replace-idx acc idx ns))

(defn process [ns]
  (:out
    (reduce
      update-acc
      {:threes-n-fours [] :out []}
      (map-indexed vector (repeat (count ns) ns)))))
