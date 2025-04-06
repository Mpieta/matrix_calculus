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

def lu_factorization_no_pivoting(A):
    n = len(A)
    A = A.astype(float)
    L = np.eye(n)
    U = A.copy()

    print_matrix(A, "Macierz wejsciowa A (14x14):")

    for i in range(n):
        pivot = U[i, i]
        if pivot == 0:
            print(f"Zerowy pivot na pozycji ({i},{i}) — brak pivotingu, może się nie udac.")
            continue

        print(f"\nKrok {i+1}: Eliminacja w kolumnie {i}")
        for j in range(i+1, n):
            multiplier = U[j, i] / pivot
            L[j, i] = multiplier
            U[j] = U[j] - multiplier * U[i]
            print_matrix(U, f"U po eliminacji wiersza {j}")
            print_matrix(L, f"L po zapisaniu mnoznika L[{j},{i}] = {multiplier:.2f}")

    print_matrix(L, "Macierz L (dolnotrojkatna):")
    print_matrix(U, "Macierz U (gornotrojkatna):")

    return L, U

# Przykład: losowa macierz całkowita (0–9)
np.random.seed(0)
A = np.random.randint(1, 50, size=(14, 14))  # bez zer, żeby uniknąć problemów bez pivotingu

# Uruchomienie faktoryzacji LU
L, U = lu_factorization_no_pivoting(A)
