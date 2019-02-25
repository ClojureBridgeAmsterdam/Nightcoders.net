(ns {{namespace}}
  (:require [reagent.core :as r]))

(defonce timer
  (r/atom (js/Date.)))

(defonce time-color
  (r/atom "red"))

(defonce time-updater
  (js/setInterval
   (fn []
     (reset! timer (js/Date.)))
   1000))

(defn greeting [message]
  [:h1 message])

(defn add-hours
  "Adds hours (number) to date."
  [date hours]
  (let [current-hours (.getHours date)]
    (doto date
      (.setHours (+ current-hours hours)))))

(defn clock [city time-str]
  [:div.example-clock
   [:div (str city ": ")]
   [:div
    {:style {:color @time-color}}
    time-str]])

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @time-color
            :on-change (fn [event]
                         (reset! time-color (-> event .-target .-value)))}]])

(def time-differences
  {"Amsterdam" 0
   "Paris" 0
   "London" -1
   "Abu Dhabi" 3
   "St Petersburg" 2
   "Shanghai" 7
   "Minneapolis" -7
   "Montreal" -6
   "Rome" 0})

(defn make-time-str [datetime]
  (-> datetime
      .toTimeString
      (clojure.string/split " ")
      first))

(def base-url
  (str (-> js.window .-location .-protocol)
       "//"
       (-> js/window .-location .-host)
       "/flags/"))

(def flag-urls
  [(str base-url "netherlands.png")
   (str base-url "france.png")
   (str base-url "united-kingdom.png")
   (str base-url "united-arab-emirates.png")
   (str base-url "russia.png")
   (str base-url "china.png")
   (str base-url "united-states-of-america.png")
   (str base-url "canada.png")
   (str base-url "italy.png")])

(defn flag-img [url]
  [:img.flag {:src url}])

;; Use for in function below to show all flags using flag-img and the flag-urls.
(defn flag-images []
  [:div
   (for [url flag-urls]
     [flag-img url])])

(defn page []
  [:div
   [greeting "Hello world, it is now"]
   [:div
    (doall
     (for [[city time-diff] time-differences]
       (let [time-str (-> @timer
                          (add-hours time-diff)
                          make-time-str)]
         [clock city time-str])))]
   [color-input]
   [flag-images]])

(r/render-component [page] (.querySelector js/document "#content"))
