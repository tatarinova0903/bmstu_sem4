#include "camera_command.h"

add_camera::add_camera(const double x,
                     const double y,
                     const double z)
    : x_pos(x), y_pos(y), z_pos(z) {}

void add_camera::execute()
{
    _scene_manager->add_camera(x_pos, y_pos, z_pos);
}


remove_camera::remove_camera(const size_t &cn)
    : camera_n(cn) {}

void remove_camera::execute()
{
     _scene_manager->remove_camera(camera_n);
}


set_camera::set_camera(const size_t &cam_n)
    : camera_n(cam_n) {}

void set_camera::execute()
{
     _scene_manager->set_cam(camera_n);
}


count_camera::count_camera(std::shared_ptr<size_t> &c)
    : count_(c) {}

void count_camera::execute()
{
    (*count_) = _scene_manager->get_models_count();
}


move_camera::move_camera(const size_t &cam_n,
                       const double &x,
                       const double &y)
    : camera_n(cam_n), shift_x(x), shift_y(y) {}

void move_camera::execute()
{
//    point s(shift_x, shift_y, 0);
//    auto camera = _scene->get_cams().at(camera_n);
//    _reform_manager->reform_object(camera, s, s, s);
}


