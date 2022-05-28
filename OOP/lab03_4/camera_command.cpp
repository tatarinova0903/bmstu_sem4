#include "camera_command.h"
#include "scene_manager_creator.h"
#include "reform_manager_creator.h"
#include "camera.h"

add_camera::add_camera(const double x,
                     const double y,
                     const double z)
    : x_pos(x), y_pos(y), z_pos(z) {}

void add_camera::execute()
{
    point cp(x_pos, y_pos, z_pos);

    std::shared_ptr<camera> cam(new camera);
    cam->reform(cp, cp, cp);

    auto sm = scene_manager_creator().create_man();
    sm->get_scene()->add_camera(cam);
    sm->set_cam(sm->get_scene()->get_cams().get_size() - 1);
}

remove_camera::remove_camera(const size_t &cn)
    : camera_n(cn) {}

void remove_camera::execute()
{
     scene_manager_creator().create_man()->get_scene()->remove_camera(camera_n);
}

move_camera::move_camera(const size_t &cam_n,
                       const double &x,
                       const double &y)
    : camera_n(cam_n), shift_x(x), shift_y(y) {}

void move_camera::execute()
{
    point s(shift_x, shift_y, 0);
    auto camera = scene_manager_creator().create_man()->get_scene()->get_cams().at(camera_n);
    reform_manager_creator().create_man()->reform_object(camera, s, s, s);
}

set_camera::set_camera(const size_t &cam_n)
    : camera_n(cam_n) {}

void set_camera::execute()
{
     scene_manager_creator().create_man()->set_cam(camera_n);
}

count_camera::count_camera(std::shared_ptr<size_t> &c)
    : count_(c) {}

void count_camera::execute()
{
    (*count_) = scene_manager_creator().create_man()->get_scene()->get_cams().get_size();
}


