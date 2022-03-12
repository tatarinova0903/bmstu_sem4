#include "matrix.h"

void free_matrix(matrix_t mat, size_t n)
{
    for (size_t i = 0; i < n; i++)
        free(mat[i]);
    free(mat);
}

return_code allocate_matrix(matrix_t &matrix, size_t n)
{
    if (!n)
    {
        return ERR_MEMORY;
    }

    int **new_matrix = (int **)calloc(n, sizeof(int *));
    if (!new_matrix)
    {
        return ERR_MEMORY;
    }

    return_code rc = OK;
    size_t i;
    for (i = 0; i < n && !rc; i++)
    {
        new_matrix[i] = (int *)calloc(n, sizeof(int));
        if (!new_matrix[i])
        {
            rc = ERR_MEMORY;
        }
    }
    if (rc == OK)
    {
        matrix = new_matrix;
    }
    else
    {
        free_matrix(new_matrix, i);
    }

    return rc;
}



