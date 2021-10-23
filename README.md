# PRiR_LAB2_2
Projekt nr 2 z laboratoriów nr 2. Program do obliczania całek oznaczonych metodami:trapezów, prostokątów i simpsona na wątkach.
Program na start pyta o a i b które są zakresami całki a następnie tworzy dla każdej metody po 100 wątków(aby wynik był w miarę dokładny) i je uruchamia. Każdy wątek przyjmuje
zakres który ma obliczyć i zwraca wynik tego obszaru. Na końcu wyniki są sumowane dzięki czemu dostajemy wynik całki.

Opis kodu
przypisanie wartości takich jak krok czy liczba wątków a także zczytanie z klawiatury a i b

        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj a:");
        double a = scanner.nextDouble();
        System.out.println("Podaj b:");
        double b = scanner.nextDouble();
        double wynik = 0.0;
        int n = 100,np = 1;
//        double a, b;
        double krok = (b - a) / (double)n;
        
 Stworzenie listy obiektów klasy M_Trapez i uruchomienie ich aby obliczyły swoje obszary a następnie jak wszystkie skończą dodają do wyniku, który jest wyświetlany na końcu
        
        ArrayList<M_Trapezow> calka = new ArrayList<>();

        for (double i = a; i < b - krok; i += krok) {
           M_Trapezow t = new M_Trapezow(i, i+krok);
            calka.add(t);
            t.start();
        }

        try {
            for(M_Trapezow t : calka) {
                t.join();
            }
        }catch(Exception e) {

        }
        for(M_Trapezow t : calka) {
            wynik += t.getWynik();
        }

        System.out.println("Metoda trapezow = " + wynik);
        
  Stworzenie listy obiektów klasy M_Prostokatow i uruchomienie ich aby obliczyły swoje obszary a następnie jak wszystkie skończą dodają do wyniku, który jest wyświetlany na końcu
  
        wynik = 0.0;

        for (double i = a; i < b - krok; i += krok) {
            M_Prostokatow t = new M_Prostokatow(i, i+krok);
            calkaP.add(t);
            t.start();
        }

        try {
            for(M_Prostokatow t : calkaP) {
                t.join();
            }
        }catch(Exception e) {

        }
        for(M_Prostokatow t : calkaP) {
            wynik += t.getWynik();
        }

        System.out.println("Metoda prostokatow = " + wynik);
        
        
    Stworzenie listy obiektów klasy M_Simpsona i uruchomienie ich aby obliczyły swoje obszary a następnie jak wszystkie skończą dodają do wyniku, który jest wyświetlany na końcu
  
        ArrayList<M_Simpsona> calkaS = new ArrayList<>();
        wynik = 0.0;

        for (double i = a; i < b - krok; i += krok) {
            M_Simpsona t = new M_Simpsona(i, i+krok);
            calkaS.add(t);
            t.start();
        }

        try {
            for(M_Simpsona t : calkaS) {
                t.join();
            }
        }catch(Exception e) {

        }
        for(M_Simpsona t : calkaS) {
            wynik += t.getWynik();
        }

        System.out.println("Metoda simpsona = " + wynik);
        
   
   Klasa M_Trapezow posiada konstruktor ustawiający zmienne metodę getWynik zwracający wynik obliczeń dla obszaru i metodę run liczącą polę obszaru zgodnie z metodą trapezów
   
       class M_Trapezow extends Thread {

        double xp, xk, n, wynikObliczen;
        public M_Trapezow(double xp, double xk)
        {
            this.xp = xp;
            this.xk = xk;
            this.wynikObliczen = 0.0;
            this.n = 10;
        }

        @Override
        public void run()
        {
            double dx = (this.xk - this.xp) / this.n;
            for(int i = 1; i < n; i++) {
                this.wynikObliczen += Main.f_x(xp + i * dx);
            }
            this.wynikObliczen += (Main.f_x(this.xp) + Main.f_x(this.xk)) / 2;
            this.wynikObliczen *= dx;
        }

        public double getWynik() {
            return this.wynikObliczen;
        }
    }
    
    
  Klasa M_Prostokatow posiada konstruktor ustawiający zmienne metodę getWynik zwracający wynik obliczeń dla obszaru i metodę run liczącą polę obszaru zgodnie z metodą prostokątów
   
         class M_Prostokatow extends Thread {

          double xp, xk, n, wynikObliczen;
          public M_Prostokatow(double xp, double xk)
          {
              this.xp = xp;
              this.xk = xk;
              this.n = 10;
              this.wynikObliczen = 0.0;

          }

          @Override
          public void run()
          {
              double dx = (this.xk - this.xp) / this.n;

              this.wynikObliczen = 0;
              for (int i=1; i<=n; i++) {
                  this.wynikObliczen += Main.f_x(this.xp + i * dx);
              }
              this.wynikObliczen *= dx;
          }

          public double getWynik() {
              return this.wynikObliczen;
          }
    }

Klasa M_Simpsona posiada konstruktor ustawiający zmienne metodę getWynik zwracający wynik obliczeń dla obszaru i metodę run liczącą polę obszaru zgodnie z metodą simpsona

    class M_Simpsona extends Thread {

        double xp, xk, n, wynikObliczen;
        public M_Simpsona(double xp, double xk)
        {
            this.xp = xp;
            this.xk = xk;
            this.wynikObliczen = 0.0;
            this.n = 10;
        }

        @Override
        public void run()
        {
            double s = 0, x, dx = (this.xk - this.xp) / this.n;
            for (int i = 1; i < n; i++) {
                x = this.xp + i * dx;
                s += Main.f_x(x - dx / 2);
                this.wynikObliczen += Main.f_x(x);
            }
            s += Main.f_x(xk - dx / 2);
            this.wynikObliczen = (dx / 6) * (Main.f_x(this.xp) + Main.f_x(this.xk) + 2 * this.wynikObliczen + 4 * s);
        }

        public double getWynik() {
            return this.wynikObliczen;
        }
    }
