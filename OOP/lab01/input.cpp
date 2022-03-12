#include "input.h"
#include "matrix.h"
#include "points.h"
#include "figure.h"

void rewind_file(FILE *stream)
{
    rewind(stream);
}

return_code read_line_point(FILE *f, point_t &p)
{
   int n;
   double x, y, z;
   if (fscanf(f, "%d %lf %lf %lf", &n, &x, &y, &z) == 4)
   {
       set_point(p, x, y, z, n);
       return OK;
   }
   return ERR_INPUT;
}

return_code read_line_matrix(FILE *f, int &i, int &j)
{
   if (fscanf(f, "%d->%d", &i, &j) == 2)
   {
       return OK;
   }
   return ERR_INPUT;
}

int points_amount(FILE *f)
{
    point_t p;
    size_t num = 0;
    while (read_line_point(f, p) == OK)
    {
        num++;
    }
    rewind_file(f);
    return num;
}

return_code read_from_file(FILE *f, figure &fig)
{
    if (!f)
    {
        return ERR_EMPTY;
    }

    return_code rc = OK;
    set_fig_n(fig, points_amount(f));
    rc = alloc_fig(fig, get_fig_n(fig));
    if (rc == OK)
    {
        rc = fill_fig(f, fig, get_fig_n(fig));
        if (rc != OK)
        {
            free_fig(fig);
        }
    }
    return rc;
}
