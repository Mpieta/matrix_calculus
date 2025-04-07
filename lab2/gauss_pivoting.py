import numpy as np

def print_matrix(mat, title="", cols_per_row=4):
    if title:
        print(f"\n{title}")
    rows, cols = mat.shape
    for i in range(rows):
        row_str = ""
        for j in range(cols):
            row_str += f"{mat[i, j]:8.2f} "
            if (j + 1) % cols_per_row == 0 and j != cols - 1:
                row_str += " | "
        print(row_str)
    print("-" * 80)

def gauss_elimination_with_pivoting(A, b):
    n = len(A)
    A = A.astype(float)
    b = b.astype(float)

    #print_matrix(A, "Macierz poczatkowa A (14x14):")
    #print_matrix(b.reshape(-1, 1), "Wektor b (14x1):")

    # Macierz rozszerzona
    Ab = np.hstack([A, b.reshape(-1, 1)])

    for i in range(n):
        # Pivoting: szukamy największego elementu w kolumnie i (od wiersza i do końca)
        max_row = i + np.argmax(np.abs(Ab[i:, i]))
        if i != max_row:
            Ab[[i, max_row]] = Ab[[max_row, i]]
            #print(f"\nZamiana wierszy {i} i {max_row} (pivot):")
            #print_matrix(Ab, f"Macierz po zamianie wierszy")

        # Normalizacja wiersza
        pivot = Ab[i][i]
        if pivot == 0:
            print("Nie mozna znalezc niezerowego pivota — uklad może byc osobliwy.")
            continue
        Ab[i] = Ab[i] / pivot
        #print_matrix(Ab, f"Krok {i+1}: Normalizacja wiersza {i}")

        # Eliminacja wierszy poniżej
        for j in range(i+1, n):
            factor = Ab[j][i]
            Ab[j] = Ab[j] - factor * Ab[i]
            #print_matrix(Ab, f"Eliminacja wiersza {j} (mnoznik = {factor:.2f})")

    #print_matrix(Ab, "Macierz po eliminacji (forma schodkowa z pivotingiem):")

    # Rozwiązywanie przez podstawianie wsteczne
    x = np.zeros(n)
    for i in range(n-1, -1, -1):
        x[i] = Ab[i, -1] - np.dot(Ab[i, i+1:n], x[i+1:n])
    #print_matrix(x.reshape(-1, 1), "Rozwiazanie wektora x:")
    return x

if __name__ == "__main__":

    # Przykład: generowanie danych całkowitych
    np.random.seed(0)
    A = np.random.randint(0, 50, size=(14, 14))
    b = np.random.randint(0, 50, size=(14,))

    gauss_elimination_with_pivoting(A, b)
