#ifndef CONST_LIST_ITERATOR_IMPLRMRNT_H
#define CONST_LIST_ITERATOR_IMPLRMRNT_H

#include "const_list_iterator.h"

using namespace iterator;

template <typename T>
const_list_iterator<T>::const_list_iterator(const const_list_iterator<T>& iter):
    base_iterator<T>(iter.ptr)
{}

template <typename T>
const_list_iterator<T>::const_list_iterator(shared_ptr<Node<T>> ptr):
    base_iterator<T>(ptr)
{}

template <typename T>
const T& const_list_iterator<T>::operator *() const
{
    time_t t_time;
    t_time = time(NULL);
    if (!this->ptr.expired())
        throw iterator_exception(__FILE__, typeid(*this).name(), __LINE__, ctime(&t_time), "Invalid iterator");

    return this->ptr.lock()->get_obj();
}

template <typename T>
const shared_ptr<Node<T>> const_list_iterator<T>::operator ->() const
{
    time_t t_time;
    t_time = time(NULL);
    if (!this->ptr.expired())
        throw iterator_exception(__FILE__, typeid(*this).name(), __LINE__, ctime(&t_time), "Invalid iterator");
    return this->ptr.lock();
}

#endif // CONST_LIST_ITERATOR_IMPLRMRNT_H
