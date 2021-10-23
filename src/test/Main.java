package test;

import java.util.ArrayList;
import java.util.Scanner;

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



public class Main {

    public static double f_x(double x)
    {
        return Math.pow(x, 2) + 2 * x;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj a:");
        double a = scanner.nextDouble();
        System.out.println("Podaj b:");
        double b = scanner.nextDouble();
        double wynik = 0.0;
        int n = 100,np = 1;
//        double a, b;
        double krok = (b - a) / (double)n;

        //trapez
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

        ArrayList<M_Prostokatow> calkaP = new ArrayList<>();
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



    }
}
