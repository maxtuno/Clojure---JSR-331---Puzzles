(ns cpwp.twin-goldbach
  (:use [cpwp.core])
  (:use [clojure.java.io])
  (:import [javax.constraints 
            Problem
            ProblemFactory
            Var]))

(def problem nil)

(def top  1000)
(def twin 2)
(def num-solutions 0)

(defn prime? 
  [max]
  (not-any? zero? (map #(rem max %) (range 2 max))))

(defn get-primes-domain
  [max]
  (let [primes (cons 2 (for [x (range 3 max 2) :when (prime? x)] x))]
  (int-array primes)))

(defn make-primes-list
  [prefix l max]
  (let [?list '()]
  (for [i (range l)]
    (cond ?list (.variable problem (str prefix i) (get-primes-domain max))))))

(defn solution-twing-goldbach
  []
  (let [primes (get-primes-domain top)
        length 2
        evens  (int-array (range 2 top 2))
        n      (.variable problem "n" evens)        
        q      (make-primes-list "q" length top)]
    (.postAllDifferent problem (into [] (concat [n] q)))
    (.post problem (into-array Integer/TYPE (repeat length 1)) (into-array Var q) "=" n)
    (.post problem (.plus (first q) twin) "=" (last q))))

(defn math 
  [next-solution]
  (let [p (.getValue next-solution "q0")
        q (.getValue next-solution "q1")
        n (.getValue next-solution "n")]
    (def num-solutions (+ num-solutions 1))
    (with-open [wrtr (clojure.java.io/writer 
                       (str "/Google Drive/tmp/Goldbach/goldbach-twin-" twin ".txt") 
                       :append true)]
      (.write wrtr (str n "," p "," q "\n")))))


(doseq [i (range 2 top 2)]
  (def twin i)
  (def problem (ProblemFactory/newProblem (str "Goldbach's Conjecture: t=" twin))) 
  (def num-solutions 0)
  (solve-math problem solution-twing-goldbach math)
  (with-open [wrtr (clojure.java.io/writer 
                       (str "/Google Drive/tmp/Goldbach/goldbach-twin.txt") 
                       :append true)]
      (.write wrtr (str twin "," num-solutions "\n"))))
