# Kubernetes (K8s)

흔히 `Docker`라고 알려진 Linux 컨테이너(이하 컨테이너) 작업을 
자동화 하는 오픈소스 플랫폼이다.

쿠버네티스는(이하 K8s) 컨테이너화 된 애플리케이션을 배포하고 확장하는데 자동이다.

즉, 컨테이너를 실행하는 호스트 그룹을 함께 클러스터링 할 수 있으며, K8s를 통해 관리한다.

처음 내가 접했을 때는 "Docker랑 다른 게 뭐지?" 라고 느꼈지만,

명확하게 달랐다. Docker를 ***배포***를 자동으로 해주는 것이 다른 것이었다.

고로 더 상위 개념으로 작동(?) 되는 느낌이다.

## 구성 요소

### Master

- etcd

  Key-value 기반의 데이터 저장소. K8s가 관리하는 데이터를 모두 관리한다.

  안정성을 위해 etcd 자체를 클러스터로 구성할 수 있다.

- kube-apiserver

  K8s에 들어오는 모든 API호출을 담당한다.

  호출된 API의 유효성을 검증하고, 실제 서비스로 전달한다.

- kube-scheduler

  클러스터 내 여러 노드(node)중, 자원 할당이 가능한 노드를 선택하여 포드(pod)를 생성한다. 

  특정 노드에 데이터가 있는 경우 그 노드를 지정하여 할당도 가능하다.

- kube-controller-manager

  포드(pod)는 각각의 컨트롤러에 의해 컨트롤 된다. manager는 이러한 컨트롤러를 관리하는 역할이다.

- cloud-controller-manager

  클라우드 서비스를 제공 해 주는 곳들에서 K8s의 컨트롤러들을 자신들의 서비스와 연계해서 사용하기 위해 사용한다.

  해당 manager는 각 클라우드 서비스 제공자가 관리하도록 되어있다.

  해당 manager는 4가지 컨트롤러로 구성된다.

  - Node Controller

    클라우드 내에서 노드를 관리하기 위해 사용되는 Controller

  - Route Controller

    각 클라우드 인프라 내에서 네트워크 라우팅을 관리하는데 사용한다

    K8s는 네트워크를 자체적으로 관리한다. 공유기 처럼 내부 IP를 제공하고 SSL 인증서를 자체적으로 생성해 https통신을 내부통신으로 사용한다.

  - Service Controller

    각 클라우드 서비스에서 제공하는 로드밸런서를 생성/갱신/삭제하는데 사용한다.

  - Volume Controller

    클라우드 서비스에서 볼륨을 생성해서 노드에 붙이고, 마운트해서 볼륨을 관리하는데 사용한다.

### Node

노드는 kubelet, kube-proxy, Container Runtime으로 구성된다.

- kubelet

  포드(pod)내 컨테이너들이 실행되는 것을 직접적으로 관리하는 역할.

- kube-proxy

  클러스터 내부 별도의 가상 네트워크를 설정하고 관리.

  가상 네트워크가 동작할 수 있게 하는 실질적인 역할을 하는 프로세스

- Container Runtime

  Docker등 실제로 컨테이너를 실행하는 구현체.

  런타임 규격을 구현하고있는 Container Runtime이라면 쿠버네티스에서 사용이 가능하다.

### addon

- 네트워킹 애드온

  K8s는 클러스터 내부에 가상네트워크를 구성해서 사용하는데,

  이때 `kuby-proxy`이외 네트워킹 애드온을 사용한다.

- DNS 애드온

  K8s 서비스들에 DNS레코드를 제공하는 역할.

  K8s 내부에서 실행된 컨테이너들은 자동으로 DNS서버에 등록된다.

- 대시보드 애드온

  클러스터 현황을 한눈에 파악을 가능하게 해주는 애드온

- 컨테이너 자원 모니터링

  클러스터 내부에서 실행중인 컨테이너의 상태를 모니터링하는 애드온.

  cpu, 메모리같은 필요한 데이터를 저장하고, 볼 수 있게 해주는 애드온.

- 클러스터 로깅

  클라우드 서비스 업체가 제공하는 인스턴스를 이용할 경우, 서비스 업체가 로그를 수집 해주지만, 자체적으로 구성 시에는 로그 수집 방안을 세워야 한다.

  보통 ELK(**E**lasticsearch + **L**ogstash + **K**ibana)를 많이 사용한다.

## 겪은 문제

- zuul을 이용한 프로젝트에서 모든 Request가 됐다 안됐다 하는 현상(404 or 정상 작동)이 있었다.

  - 원인 : Kube.yml을 잘못 작성하여 내 pod가 zuul pod이 붙어있는 service에 붙어 gate-way service에 두개의 pod가 붙어있었다.

    원인을 알게 된 것은 `kubectl get service`를 했고, 

    해당 서비스를 `kubectl get service [서비스이름]`조회하여 서비스 이름을 조회하고,

    `kubectl describe service [서비스이름]`으로 describe를 확인하여 붙어있는 pod들의 ip를 확인할 수 있었다.

    이로 인해 service가 RoundRobin으로 제공되어 서비스가 제공됐다 안됐다 하는 현상을 확인할 수 있었다.

  - 해결 

    개발환경은 젠킨스와 harbor를 이용한 자동배포를 이용하고 있으므로,

    pod을 재 배포 한다 하더라도, 기존 kube.yml을 참조하고 pod을 생성했으므로, 설정이 바뀌지 않는다.

    강제적으로 img를 삭제하고 다시 올려도 `imgpullbackOff`로 전환이 되는 것을 확인했다.

    그래서 Deployment를 삭제하고 다시 재 배포를 진행하였다.

    `kubectl get deployments` : Deploy 조회

    `kubectl delete deployment [deployment 이름]` : Deploy 삭제

    > (필자는 여기서 젠킨스를 재 배포 진행하였으나, 수동으로 적용시 아래 명령어를 입력하면 될듯하다.)

    `kubectl apply -f [kube.yml 파일의 위치 경로]` : 재 배포 적용



# :pencil: 작성 중입니다. :innocent:

