#include "input.h"
#include "mainwindow.h"
#include "functions.h"
#include "action.h"
#include "controller.h"

return_code download_model(figure_t &fig, data_t act)
{
    FILE *file;
    file = fopen(get_filename(act), "r");
    return_code rc = OK;
    if (!file)
    {
        rc = ERR_OPEN_FILE;
    }
    else
    {
        rc = read_from_file(file, fig);
        fclose(file);
    }
    return rc;
}

void move_point(point_t &a, double dx, double dy, double dz)
{
    double x = get_point_x(a) + dx;
    double y = get_point_y(a) + dy;
    double z = get_point_z(a) + dz;
    set_point_x(a, x);
    set_point_y(a, y);
    set_point_z(a, z);
}

return_code move_fig(figure_t &fig, data_t act)
{
    return_code rc = OK;
    if (is_empty(fig))
    {
        rc = ERR_EMPTY;
    }
    else
    {
        double dx = get_deltax(act);
        double dy = get_deltay(act);
        double dz = get_deltaz(act);
        for (size_t i = 0; i < get_fig_n(fig); i++)
        {
            move_point(get_point(fig, i), dx, dy, dz);
        }
    }
    return rc;
}

void rotate_ax(point_t &a, double ax)
{
    double ya = get_point_y(a);
    double za = get_point_z(a);
    double alpha = ax * M_PI / 180.0;
    double cosa = cos(alpha);
    double sina = sin(alpha);
    double z = za * cosa - ya * sina;
    double y = za * sina + ya * cosa;
    set_point_z(a, z);
    set_point_y(a, y);
}

void rotate_ay(point_t &a, double ay)
{
    double xa = get_point_x(a);
    double za = get_point_z(a);
    double alpha = ay * M_PI / 180.0;
    double cosa = cos(alpha);
    double sina = sin(alpha);
    double x = xa * cosa + za * sina;
    double z = -xa * sina + za * cosa;
    set_point_x(a, x);
    set_point_z(a, z);
}

void rotate_az(point_t &a, double az)
{
    double xa = get_point_x(a);
    double ya = get_point_y(a);
    double alpha = az * M_PI / 180.0;
    double cosaz = cos(alpha);
    double sinaz = sin(alpha);
    double x = xa * cosaz - ya * sinaz;
    double y = xa * sinaz + ya * cosaz;
    set_point_x(a, x);
    set_point_y(a, y);
}

void rotate_point(point_t &a, alpha_t alpha)
{
    double ax = get_alphax(alpha);
    double ay = get_alphay(alpha);
    double az = get_alphaz(alpha);
    rotate_az(a, az);
    rotate_ax(a, ax);
    rotate_ay(a, ay);
}

return_code rotate_fig(figure_t &fig, data_t act)
{
    return_code rc = OK;
    if (is_empty(fig))
    {
        rc = ERR_EMPTY;
    }
    else
    {
        alpha_t alpha = get_alpha(act);
        for (size_t i = 0; i < get_fig_n(fig); i++)
        {
            rotate_point(get_point(fig, i), alpha);
        }
    }
    return rc;
}

void scale_point(point_t &a, double k)
{
    double x = k * get_point_x(a);
    double y = k * get_point_y(a);
    double z = k * get_point_z(a);
    set_point_x(a, x);
    set_point_y(a, y);
    set_point_z(a, z);
}

return_code scale_fig(figure_t &fig, data_t act)
{
    return_code rc = OK;
    if (is_empty(fig))
    {
        rc = ERR_EMPTY;
    }
    else
    {
        double k = get_k(act);
        for (size_t i = 0; i < get_fig_n(fig); i++)
        {
            scale_point(get_point(fig, i), k);
        }
    }
    return rc;

}

void clear_fig(figure_t &fig)
{
    free_fig(fig);
}

void draw_fig(figure_t &fig, myscene_t scene)
{
    clear_scene(scene);
    draw_model(fig, scene);
}

void draw_model(figure_t fig, myscene_t scene)
{
    for (size_t i = 1; i < get_fig_n(fig); i++)
    {
        for (size_t j = 0; j < i; j++)
        {
            if (get_matrix_el(fig, i, j) != 0)
            {
                draw_line_scene(scene, get_point(fig, i), get_point(fig, j));
            }
        }
    }
}
