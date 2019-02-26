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

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @time-color
            :on-change (fn [event]
                         (reset! time-color (-> event .-target .-value)))}]])

(def base-url
  (str (-> js/window .-location .-protocol)
       "//"
       (-> js/window .-location .-host)
       "/flags/"))

(def city-data
  {"Amsterdam"     {:time-diff 0 :flag-url (str base-url "netherlands.png")}
   "Paris"         {:time-diff 0 :flag-url (str base-url "france.png")}
   "London"        {:time-diff -1 :flag-url (str base-url "united-kingdom.png")}
   "Abu Dhabi"     {:time-diff 3 :flag-url (str base-url "united-arab-emirates.png")}
   "St Petersburg" {:time-diff 2 :flag-url (str base-url "russia.png")}
   "Shanghai"      {:time-diff 7 :flag-url (str base-url "china.png")}
   "Minneapolis"   {:time-diff -7 :flag-url (str base-url "united-states-of-america.png")}
   "Montreal"      {:time-diff -6 :flag-url (str base-url "canada.png")}
   "Rome"          {:time-diff 0 :flag-url (str base-url "italy.png")}})

(defn make-time-str [datetime]
  (-> datetime
      .toTimeString
      (clojure.string/split " ")
      first))

(defn clock [city time-str flag-url]
  [:div.clock
   [:img {:src flag-url}]
   [:div (str city ": ")]
   [:div
    {:style {:color @time-color}}
    time-str]])

;; Use for in function below to .........
(defn blabla []
  [:div
   (doall
    (for [x (range 10)]
      x))])

(defn page []
  [:div
   [greeting "Hello world, it is now"]
   (doall
    (for [[city {:keys [time-diff flag-url]}] city-data]
      (let [time-str (-> @timer
                         (add-hours time-diff)
                         make-time-str)]
        ^{:key city}
        [clock city time-str flag-url])))
   [color-input]])

(r/render-component [page] (.querySelector js/document "#content"))
