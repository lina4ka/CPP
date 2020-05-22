public class Primes {

    public static boolean isPrime(int n)
    {
        boolean isItPrime = true;
        for (int i = 2; i< n; i++)
            if (n % i == 0) {
                isItPrime = false;
                break;
            }
        return isItPrime;
    }
    public static void main(String[] args)
    {
        for (int i = 2; i < 100; i++ )
        {
            if (isPrime(i))
                System.out.print(i+" ");
        }
    }
}