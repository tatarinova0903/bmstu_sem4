#ifndef SCENE_MANAGER_H
#define SCENE_MANAGER_H

#include "camera.h"
#include "scene.h"
#include "base_manager.h"

class scene_manager : public base_manager
{
public:
    scene_manager();
    ~scene_manager() = default;

    void add_model(std::shared_ptr<object> model);
    void remove_model(const size_t model_index);

    void add_camera(const double x, const double y, const double z);
    void remove_camera(const size_t cam_index);

    std::shared_ptr<scene> get_scene() const;
    std::shared_ptr<camera> get_cam() const;

    size_t get_models_count();

    void set_scene(std::shared_ptr<scene> scene_);
    void set_cam(const size_t &cam_numb);
    void next_cam();

private:
    std::shared_ptr<scene> _scene;
    std::shared_ptr<camera> current_cam;
};

#endif
