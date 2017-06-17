(ns numerology.core-test
  (:require [clojure.test :refer :all]
            [numerology.core :refer :all]))

(deftest a-test
  (testing "replaces 9 with two tens"
    (is (= [10 10] (process [9])))
    (is (= [0 10 10 0] (process [0 9 0]))))

  (testing "replaces 2 with an equal amount of 1s as the number to the left"
    (is (= [1 1] (process [1 2])))
    (is (= [0] (process [0 2])))))
