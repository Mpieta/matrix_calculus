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

def lu_factorization_with_pivoting(A):
    n = len(A)
    A = A.astype(float)

    L = np.eye(n)
    U = A.copy()
    P = np.eye(n)

    #print_matrix(A, "Macierz wejsciowa A (14x14):")

    for i in range(n):
        # Pivoting: znajdź największy element w kolumnie i (od wiersza i do końca)
        max_row = i + np.argmax(np.abs(U[i:, i]))
        if U[max_row, i] == 0:
            raise ValueError("Zerowy pivot — układ osobliwy")

        if max_row != i:
            # Zamiana wierszy w U, P i dotychczasowej L
            U[[i, max_row]] = U[[max_row, i]]
            P[[i, max_row]] = P[[max_row, i]]
            if i > 0:
                L[[i, max_row], :i] = L[[max_row, i], :i]
            #print(f"\nZamiana wierszy {i} <-> {max_row} (pivoting):")
            #print_matrix(U, "U po zamianie:")
            #print_matrix(P, "P po zamianie:")

        pivot = U[i, i]

        # Eliminacja poniżej
        for j in range(i+1, n):
            multiplier = U[j, i] / pivot
            L[j, i] = multiplier
            U[j] = U[j] - multiplier * U[i]
            #print_matrix(U, f"U po eliminacji wiersza {j}")
            #print_matrix(L, f"L po zapisaniu mnożnika L[{j},{i}] = {multiplier:.2f}")

    #print_matrix(P, "Macierz permutacji P:")
    #print_matrix(L, "Macierz L (dolnotrojkatna):")
    #print_matrix(U, "Macierz U (gornotrojkatna):")

    return P, L, U

if __name__ == "__main__":
    np.random.seed(0)
    A = np.random.randint(1, 50, size=(14, 14))

    P, L, U = lu_factorization_with_pivoting(A)


    print_matrix(P @ A, "PA")
    print_matrix(L @ U, "LU")
    print("Czy PA = LU?", np.allclose(P @ A, L @ U))
