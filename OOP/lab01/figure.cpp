#include "figure.h"
#include "input.h"
#include "memory.h"

return_code alloc_fig(figure_t &fig, size_t n)
{
    return_code rc = allocate_arr(fig.arr, n);
    if (rc == OK)
    {
        rc = allocate_matrix(fig.matrix, n);
        if (rc != OK)
        {
            free_fig(fig);
        }
    }
    return rc;
}

return_code create_arr(point_t *arr, size_t n, FILE *f)
{
    if (!f || !n || !arr)
        return ERR_INPUT;
    struct point p;

    return_code rc = OK;
    for (size_t i = 0; i < n && !rc; i++)
    {
        if (read_line_point(f,p) != OK)
        {
            rc = ERR_INPUT;
        }
        else
        {
             copy_point(arr[i],p);
        }
    }
    return rc;
}

return_code create_matrix(matrix_t mt, size_t n, FILE *f)
{
    if (!f || !n || !mt)
        return ERR_INPUT;

    int mi,mj;
    while (read_line_mt_el(f,mi,mj) == OK)
    {
        mt[mi-1][mj-1] = 1;
        mt[mj-1][mi-1] = 1;
    }
    return OK;
}

return_code create_fig(figure_t &fig, size_t n, FILE *f)
{
    return_code rc = OK;
    rc = create_arr(fig.arr, n, f);
    if (rc) return rc;
    rc = create_matrix(fig.matrix,n,f);
    return rc;
}

size_t &get_fig_n(struct figure &fig)
{
    return fig.n;
}

int is_empty(struct figure &fig)
{
    return !(fig.arr && fig.matrix && get_fig_n(fig));
}

void free_fig(struct figure &fig)
{
    if (fig.arr)
        delete [] fig.arr;
    if (fig.matrix)
        free_matrix(fig.matrix, fig.n);

    fig.n = 0;
    fig.matrix = NULL;
    fig.arr = NULL;
}

figure_t init_fig()
{
    figure_t fig;
    fig.n = 0;
    fig.matrix = NULL;
    fig.arr = NULL;
    return fig;
}

int get_matrix_el(figure_t fig, size_t i, size_t j)
{
    return fig.matrix[i][j];
}

void set_fig_matrix(figure_t &fig, matrix_t mt)
{
    fig.matrix = mt;
}

void set_fig_arr(figure_t &fig, point_t *arr)
{
    fig.arr = arr;
}

void set_fig_n(figure_t &fig, size_t n)
{
    fig.n = n;
}
