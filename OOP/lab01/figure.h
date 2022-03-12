#ifndef FIGURE_H
#define FIGURE_H

#include "points.h"
#include "matrix.h"

typedef struct figure figure_t;

struct figure
{
    point_t *arr;
    size_t n;
    int **matrix;
};

size_t &get_fig_n(figure_t &fig);
int is_empty(figure_t &fig);
void free_fig(figure_t &fig);
figure_t init_fig();
int get_matrix_el(figure_t fig, size_t i, size_t j);
point_t &get_array_el(figure_t &fig, size_t i);
void set_fig_n(figure_t &fig, size_t n);
void set_fig_arr(figure_t &fig, point_t *arr);
void set_fig_matrix(figure_t &fig, matrix_t matr);
void copy_fig(figure_t &fig, figure_t &work);
return_code alloc_fig(figure_t &fig, size_t n);
return_code fill_fig(FILE *f, figure_t &fig, size_t n);

#endif // FIGURE_H
