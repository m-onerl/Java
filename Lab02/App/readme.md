
W ramach laboratorium z przedmiotu "Programowanie w języku Java - techniki zaawansowane" przeprowadzilem testy mające na celu zbadanie wpływu różnych flag konfiguracyjnych wirtualnej maszyny javy  na wydajnosc i zarządzanie pamięcią aplikacji. 

java -Xms512m -XX:+UseG1GC -jar App.jar
java -Xmx1024m -XX:+UseSerialGC -jar App.jar

-XX:+UseSerialGC,
 -XX:+UseParallelGC,
  -XX:+UseG1GC,
   -XX:+ShrinkHeapInSteps,
      -XX:-ShrinkHeapInSteps.

Flaga -Xms512m ustala początkowy rozmiar sterty na 512 MB, co pozwala na alokację tej pamięci od momentu startu JVM, zwiększając wydajność początkową aplikacji.
Flaga -Xmx1024m ogranicza maksymalny rozmiar sterty do 1024 MB, co jest górną granicą pamięci dostępnej dla obiektów aplikacji.
Flaga -XX:+ShrinkHeapInSteps umożliwia JVM stopniowe zmniejszanie sterty podczas zbierania śmieci, co może przyczynic się do efektywniejszego zarządzania pamięcią.
Flaga -XX:+UseG1GC aktywuje algorytm zbierania śmieci G1, który jest optymalizowany pod kątem aplikacji z dużą iloscia pamięci i wymaganiami niskiego opóźnienia.
Flagi -XX:+UseSerialGC i -XX:+UseParallelGC odnoszą się do różnych algorytmów garbage collectora, które mogą być lepiej dopasowane do aplikacji

Z testowanie aplikacji wywnioskowałem, że odpowiednia konfiguracja flag wirtualnej maszyny javy może znacząco wpłynąć na wydajność algorytmów w aplikacji. Aplikacji wymagające intensywnego zarządzania pamięcią i niskiej latencji Garbage collectora, jak nasza testowa aplikacja Swing, wybór flagi -XX:+UseG1GC jest dla najbardziej optymalne. Z kolei dla aplikacji z mniejszym zapotrzebowaniem na pamięć, jednowątkowym, lepszą flagą bedzie -XX:+UseSerialGC.
