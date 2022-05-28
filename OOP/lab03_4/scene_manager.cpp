#include "scene_manager.h"

scene_manager::scene_manager()
{
    this->_scene = std::shared_ptr<scene>(new scene);
}

void add_camera(const double x, const double y, const double z)
{

}

void remove_camera(const size_t cam_index)
{

}

size_t scene_manager::get_models_count() {
    return this->_scene->get_models().get_size();
}

std::shared_ptr<scene> scene_manager::get_scene() const
{
    return this->_scene;
}

std::shared_ptr<camera> scene_manager::get_cam() const
{
    return this->current_cam;
}

void scene_manager::set_scene(std::shared_ptr<scene> scene_)
{
    this->_scene = scene_;
}

//void scene_manager::add_camera(const double x, const double y, const double z)
//{
//    this->_scene->add_camera(x, y, z);
//    set_cam(_scene->get_cams().get_size() - 1);
//}

void scene_manager::set_cam(const size_t &number)
{
    auto _camera = _scene->get_cams().at(number);
    this->current_cam = _camera;
}

void scene_manager::next_cam()
{

}

