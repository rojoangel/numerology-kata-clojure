(ns numerology.core-test
  (:require [clojure.test :refer :all]
            [numerology.core :refer :all]))

(deftest a-test
  (testing "replaces 9 with two tens"
    (is (= [10 10] (process [9])))
    (is (= [0 10 10 0] (process [0 9 0]))))

  (testing "replaces 2 with an equal amount of 1s as the number to the left"
    (is (= [1 1] (process [1 2])))
    (is (= [0] (process [0 2]))))

  (testing "replaces 6 with an equal amount of 3's as the value of the number which is an equal
  amount of steps to the right of the 6 as the number which is to the immediate left of it"
    (is (= [1 3 1] (process [1 6 1])))
    (is (= [0 3 3 3 3 3 3] (process [0 6]))))

  (testing "replace any 3 by a 5 if not immediately succeeded by 5 and any 4 by a 3 if not immediately preceeded by 5.
  When replacing the 3's and 4's, you may not replace more than one 3 or 4 in a go without having replaced one instance
  of the other in between.
  When four 3's have been replaced, and three 4's have been replaced no more replacements may occur until a 7 is seen.
  Once a 7 is seen, the whole process is reset and four 3's and three 4's may be replaced again"
    (is (= [5] (process [3])))
    (is (= [3 5] (process [3 5])))
    (is (= [5 5] (process [5 3])))
    (is (= [0 5] (process [0 3])))
    (is (= [3] (process [4])))))